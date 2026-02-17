# Revisió de codi — product-offers

**Data:** 16‑02‑2026

## Resum executiu ✅
L'aplicació està neta i les proves cobreixen els casos principals; la lògica de negoci (selecció de preu per prioritat) està ben coberta. Hi ha però desviacions respecte a DDD / arquitectura hexagonal i alguns problemes SOLID menors (acoblament al framework, Lombok mal aplicat, tests amb mocks inútils). Recomanacions: 2 canvis ràpids (correccions de seguretat i tests) + 1 refactor mitjà per separar ports/adapters.

---

## Troballes principals (prioritat alta → baixa)

### 1) Dependència del domini amb Spring Data / HTTP (Alta) 🔧
- Problema: `PricesRepository` està a `price.domain.repositories` i **extén `JpaRepository`** — això fa que la capa de domini depengui d'implementacions d'infraestructura.
- Problema relacionat: `ProductOffersServiceImpl` llença `ResponseStatusException` (HTTP) des del servei de domini — mescla nivells.
- Impacte: Dificulta proves aïllades, penalitza la portabilitat i viola la separació ports/adapters (Hexagonal).
- Recomanació: crear un *port* de repositori al domini (interfície pura), i un *adapter* en `infrastructure.persistence` que delegui a un `SpringData` repo.

Referències: `src/main/java/.../price/domain/repositories/PricesRepository.java`, `ProductOffersServiceImpl.java`

---

### 2) Lombok / equals-hashcode bug (Alta) ⚠️
- Problema: `Prices` té `@EqualsAndHashCode(onlyExplicitlyIncluded = true)` però **no hi ha camps marcats amb `@EqualsAndHashCode.Include`**. Això produeix equals/hashCode inesperats.
- Recomanació: o bé eliminar l'anotació o marcar `id` com a `@EqualsAndHashCode.Include`.

Fitxer: `src/main/java/.../price/domain/entities/Prices.java`

---

### 3) Excepcions i ControllerAdvice (Mitjana) 🛠️
- Problema: `ProductOffersControllerAdvice#getExceptionMessage` fa un `substring` de `exception.getMessage()` — fràgil i propens a errors. També el servei retorna `ResponseStatusException` en lloc d'una excepció de domini.
- Recomanació: utilitzar `ex.getReason()` (si existeix) o capturar una `PriceNotFoundException` de domini i mapar-la a 404 a l'advice.

Fitxers: `ProductOffersControllerAdvice.java`, `ProductOffersServiceImpl.java`

---

### 4) Logger i estil (Baixa) 📝
- Actualment s'usa `@Log` (java.util.logging). Millor passar a `@Slf4j` (SLF4J) per coherència amb l'ecosistema Spring Boot.
- Suprimeix comprovacions explícites d'`isLoggable` en favor de registres directes (SLF4J és eficient amb placeholders).

Referència: `ProductOffersServiceImpl.java`

---

### 5) Tests (Millorable) ✅→🔧
- Troballes: `ProductOffersServiceTests.testGetInquiryPricesErrorCases` mockeja `findByBrandIdAndProductId` però el service crida `findTopBy...` — el mock és innecessari/misleading.
- `testLogInfo` verifica un `Logger` mock que **no està injectat** — test inútil.
- Recomanació: arreglar tests per mockear el mètode real, afegir tests per al mapping d'excepcions i cobrir casos de prioritat/empats.

Fitxers: `src/test/java/.../ProductOffersServiceTests.java`

---

### 6) Mapper / DTO (Estil) (Baixa)
- `MapperProductOffers.entityToResponse(Long productId, Prices price)` rep `productId` extern quan `price.getProductId()` conté la mateixa informació — risc de discrepància.
- Recomanació: eliminar el paràmetre redundant i usar l'entitat com a font única.

Fitxer: `MapperProductOffers.java`, `ResponseProductOffer.java`

---

## Solucions ràpides (quick wins) — es poden fer en un PR curt
1. Corregir `@EqualsAndHashCode` a `Prices` (1–2 files). 
2. Substituir la manipulació fràgil del missatge a `ProductOffersControllerAdvice` per `getReason()` o defensiva. 
3. Arreglar tests inútils (`testLogInfo`) i els mocks equivocats en `ProductOffersServiceTests`.

Snippet suggerit (Prices):
```
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Prices {
    @Id
    @GeneratedValue...
    @EqualsAndHashCode.Include
    private Long id;
    ...
}
```

Snippet suggerit (ControllerAdvice):
```
private String getExceptionMessage(ResponseStatusException ex) {
  return Optional.ofNullable(ex.getReason()).orElse(ex.getMessage());
}
```

---

## Refactors de mitjà termini (recommended)
- Introduir ports/adapters:
  - `domain.ports.PriceRepository` (interfície pura)
  - `infrastructure.persistence.SpringDataPriceAdapter` (implementa el port i usa `PricesRepository extends JpaRepository` internament)
- Canviar `ProductOffersServiceImpl` per llençar `PriceNotFoundException` (domini) i mapar-lo a 404 a `ControllerAdvice`.
- Migrar logs a `@Slf4j`.
- Fer DTOs immutables (usar constructors o `record` si aplicable).

---

## Test strategy recomanada
- Unit tests: cobrir lògica de prioritat, empat per priority, transformacions del mapper.
- Integration tests: endpoint `/inquiry-prices` (existents — mantenir), afegir tests per errors (404, 400) i validation.
- Contract tests / API schema: afegir OpenAPI / Swagger per assegurar contracte i facilitar clients.

---

## Estimació i checklist per PR (suggerit)
- PR #1 (Hotfix, 0.5–1d): Fix Equals/hashCode + ControllerAdvice substring + test fixes. ✅
- PR #2 (Refactor, 1–3d): Introduir port/adapter per repositori i canviar excepcions de domini. ✅
- PR #3 (Improvements, 1–2d): Logging → SLF4J, DTO immutables, neteja Lombok innecessari.

Criteris d'acceptació:
- Tots els tests passen
- No referències a `ResponseStatusException` fora de la capa d'`application`
- `Prices` té equals/hashCode correcte
- Nous tests cobreixen els canvis

---

## Notes de bones pràctiques (curtes) 💡
- Mantingues les interfases de domini lliures de dependències de Spring.
- Preferir excepcions de domini i traduir-les a HTTP a la capa d'adaptador (ControllerAdvice).
- Evitar l'ús de setters públics d'entitats en la mesura del possible (encapsulació).

---

## Violacions de `Tell, don't ask` (detall) 🧭
He identificat tres àrees on el principi **"tell, don't ask"** no s'està aplicant íntegrament: l'entitat `Prices` (model anèmic), l'`ObjectMother` dels tests i el `Mapper`. Això dispersa responsabilitats (lògica de domini distribuïda) i fa més difícil encapsular regles de negoci i escriure tests expressius.

1) Entitat anèmica — `Prices` (design de domini) ⚠️
- On: `src/main/java/.../price/domain/entities/Prices.java`.
- Què: `Prices` exposa només dades (getters/setters) i no encapsula comportaments rellevants (p. ex. "aplicable en una data", "comparar prioritat").
- Per què és una violació: el codi fora de l'entitat acaba "preguntant" (asks) l'estat per aplicar regles de negoci enlloc de "dir" (tell) a l'entitat que resolgui la seva pròpia lògica.
- Recomanació (exemples): afegir mètodes de domini a `Prices` perquè respongui a preguntes comunes:

```java
public boolean isEffectiveAt(LocalDateTime when) {
  return !startDate.isAfter(when) && !endDate.isBefore(when);
}

public boolean matches(Long productId, Long brandId) {
  return this.productId.equals(productId) && this.brandId.equals(brandId);
}
```
- Benefici: trasllada la decisió al model, redueix codi repetit i facilita tests de domini.

2) `PricesObjectMother` (tests) — pregunta l'estat per filtrar ✅→❌
- On: `src/test/java/.../helpers/PricesObjectMother.java` (linia on es fa `.filter(price -> price.getBrandId().equals(brandId) && price.getProductId().equals(productId))`).
- Problema: el test-helper fa "asks" mitjançant getters per decidir coincidències; millor reutilitzar mètodes de domini o helpers explícits que expressin la intenció.
- Solució: canviar el filtre a `price.matches(productId, brandId)` (veure mètode a `Prices`) o proporcionar un fixture builder amb criteris semàntics.

3) `MapperProductOffers.entityToResponse(...)` rep `productId` per separat (redundància) 🔄
- On: `src/main/java/.../price/domain/mappers/MapperProductOffers.java`.
- Què: el mapper "pregunta" el `Prices` per camps però també accepta `productId` extern; això potencia desincronització i obliga a passar dades que l'entitat ja coneix.
- Recomanació: canviar la signatura a `entityToResponse(Prices price)` i/o delegar a `Prices` per obtenir el `productId`. Així el codi “dóna feina” al domini o elimina la font redundant d'informació.

4) Recomanacions generals i tests addicionals
- Afegir unit tests per a `Prices.isEffectiveAt(...)` i `Prices.matches(...)`.
- Quan hi hagi lògica de selecció (p. ex. seleccionar entre múltiples `Prices`), preferir aplicar `Comparator` basat en mètodes de domini en lloc d'accedir directament als camps.
- Refactor mínim: implementa els petits mètodes de domini a `Prices` i actualitza `PricesObjectMother` i `Mapper` per deixar d'usar getters directament en condicions.

Referències concretes (llocs a revisar):
- `src/main/java/net/jordimp/productoffers/price/domain/entities/Prices.java` — volem mètodes de domini.
- `src/test/java/net/jordimp/productoffers/helpers/PricesObjectMother.java` — filtrar amb `matches(...)`.
- `src/main/java/net/jordimp/productoffers/price/domain/mappers/MapperProductOffers.java` — eliminar paràmetre `productId` redundant.

Impacte estimat: 0.25–1 dia (canvis locals, tests). Aquest pas augmenta la cohesió del model i fa els tests més declaratius.

---

## Fitxers clau a canviar (resum)
- `src/main/java/.../price/domain/entities/Prices.java` — fix Lombok equals
- `src/main/java/.../price/domain/services/ProductOffersServiceImpl.java` — no llençar ResponseStatusException
- `src/main/java/.../price/domain/repositories/PricesRepository.java` — crear port/adapter si es refactora
- `src/main/java/.../price/application/errors/ProductOffersControllerAdvice.java` — fer parsing segur del missatge
- `src/test/java/.../price/domain/services/ProductOffersServiceTests.java` — corregir mocks i tests inútils

---

Si vols, creo PRs amb els canvis ràpids (fixes + tests) i després un PR separat per a la refactorització ports/adapters. Vols que comenci pel PR de fixes ràpids? 
