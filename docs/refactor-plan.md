# Pla de refactorització — product-offers

**Data:** 17‑02‑2026

## Resum executiu 🎯
Objectiu: eliminar dependències d'infraestructura de la capa de domini, corregir bugs crítics de Lombok i excepcions, millorar logging i arreglar tests. PRs petits, revisables i amb proves automàtiques com a condició d'acceptació.

---

## Roadmap (prioritzat)
1. PR #1 — Quick fixes (hotfix) ✅ (0.5–1d)
   - Fix `Prices` equals/hashCode
   - Corregir `ProductOffersControllerAdvice#getExceptionMessage`
   - Arreglar tests inútils/mocks equivocats
   - Acceptació: tots els tests passen

2. PR #2 — Ports & adapters (refactor de domini → infra) 🔁 (1–3d)
   - Introduir `domain.ports.PriceRepository` (port)
   - Crear `infrastructure.persistence` adapter que delegui a Spring Data
   - El servei depèn del port; llança `PriceNotFoundException`
   - Acceptació: cap referència a Spring Data dins `price.domain.*`

3. PR #3 — Model, mapper i logging ✨ (1–2d)
   - Afegir mètodes de domini a `Prices` (`isEffectiveAt`, `matches`)
   - Simplificar `MapperProductOffers` (eliminar paràmetres redundants)
   - Migrar `@Log` → `@Slf4j` on correspongui

4. PR #4 — Tests i neteja final 🧪 (0.5–1.5d)
   - Tests unit i d'integració nous/actualitzats (inclòs 404)
   - Millorar cobertura i passes CI

---

## Tasques concretes per arxiu 🔧
- `src/main/java/.../price/domain/entities/Prices.java`
  - Marcar `id` amb `@EqualsAndHashCode.Include`
  - Afegir `isEffectiveAt(...)` i `matches(...)`

- `src/main/java/.../price/domain/repositories/PricesRepository.java`
  - Crear port `PriceRepositoryPort` a `domain.ports`
  - Moure l'actual Spring Data repo a `infrastructure.persistence` i renombrar-lo

- `src/main/java/.../price/domain/services/ProductOffersServiceImpl.java`
  - Dependre del port i llençar `PriceNotFoundException` (no `ResponseStatusException`)

- `src/main/java/.../price/application/errors/ProductOffersControllerAdvice.java`
  - Reemplaçar substring fràgil per `ex.getReason()` o retorn segur
  - Afegir `@ExceptionHandler(PriceNotFoundException.class)` → 404

- `src/main/java/.../price/domain/mappers/MapperProductOffers.java`
  - Eliminar `productId` redundant; usar `price.getProductId()`

- Tests:
  - Corregir mocks a `ProductOffersServiceTests` i eliminar `testLogInfo` inútil
  - Afegir unit tests per `Prices.isEffectiveAt()` i `matches()`

---

## Snippets clau (ràpids) 🔍
- Fix equals/hashCode (Prices):

```java
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Prices {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    ...
}
```

- ControllerAdvice — missatge segur:

```java
private String getExceptionMessage(ResponseStatusException ex) {
  return Optional.ofNullable(ex.getReason()).orElse(ex.getMessage());
}
```

- Port d'exemple:

```java
package net.jordimp.productoffers.price.domain.ports;

public interface PriceRepositoryPort {
    Optional<Prices> findBestPrice(Long brandId, Long productId, LocalDateTime applicationDate);
}
```

---

## Estratègia de proves ✅
- Unit: `Prices` (domini), `MapperProductOffers`, `ProductOffersService` (mock del port)
- Integration: endpoint `/inquiry-prices` (success + 404)
- CI: executar `mvn test` i assegurar cobertura mínima

---

## Criteris d'acceptació (Definition of Done)
- Tots els tests passen (unit + integration)
- `Prices` té equals/hashCode correcte
- No hi ha `ResponseStatusException` fora de la capa `application`
- Domini no depèn de Spring Data (`JpaRepository` fora de `price.domain`)

---

## Branch / PR naming i commits suggerits
- Branch: `fix/prices-equals-controlleradvice-tests`
- Branch: `refactor/ports-adapters-price-repository`
- Commits: `fix(domain): include id in Prices equals/hashCode`, `refactor(service): throw PriceNotFoundException from domain`, `test: fix mocks and add Prices unit tests`

---

## Properes passes (senzilles)
1. Crear PR #1 amb fixes ràpids (equals/hash, ControllerAdvice, tests) — prioritat màxima.
2. Un cop PR #1 aprovat, implementar PR #2 (ports/adapters).

---

Si vols, començo pel PR #1 i puc aplicar els canvis i tests ara mateix. ✅
