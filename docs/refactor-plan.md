# Pla de refactorització — product-offers

**Última actualització:** 17‑02‑2026

## Resum executiu 🎯
Objectiu: eliminar dependències d'infraestructura de la capa de domini, corregir bugs crítics i millorar la qualitat del codi amb refactors per passos petits i fàcilment revisables.

Actualització ràpida: PR #1 (hotfix) complet i empès; treball de PR #2 (ports/adapters) en curs sobre la branca `fix/prices-equals-controlleradvice-tests`. Tots els tests passen localment i la cobertura JaCoCo està complerta. ✅

---

## Roadmap (prioritzat)
1. PR #1 — Quick fixes (hotfix) ✅ (complet)
   - Fix `Prices` equals/hashCode — DONE
   - Corregir `ProductOffersControllerAdvice#getExceptionMessage` — DONE
   - Arreglar tests inútils / mocks equivocats — DONE
   - Acceptació: tots els tests passen i JaCoCo OK

2. PR #2 — Ports & adapters (refactor de domini → infra) 🔁 (en curs)
   - Introduït `price.domain.ports.PriceRepositoryPort` — DONE
   - Creat `price.infrastructure.persistence.SpringDataPriceAdapter` — DONE
   - Spring Data repo mogut a `price.infrastructure.persistence` — DONE
   - `ProductOffersServiceImpl` depèn del port i llença `PriceNotFoundException` — DONE
   - Tests addicionals afegits (adapter + mapper + controller-advice) — PARTIAL / DONE
   - Pendents: eliminar stubs/placeholder restants del paquet `domain`, afegir mètodes de domini a `Prices`, completes proves d'integració del adapter
   - Acceptació: cap referència a Spring Data dins `price.domain.*` (objectiu en procés)

3. PR #3 — Model, mapper i logging ✨ (planificat)
   - Afegir `Prices.isEffectiveAt(...)` i `matches(...)` — TODO
   - Simplificar `MapperProductOffers` (eliminar paràmetres redundants) — TODO
   - Migrar `@Log` → `@Slf4j` on correspongui — TODO

4. PR #4 — Tests i neteja final 🧪 (tancament)
   - Afegir / completar unit i integració per cobrir tots els casos (inclòs 404) — TODO
   - Eliminar interfaces-placeholder i netejar imports/poms — TODO

---

## Estat a vista d'ocell ✅ / ⚠️
- Branch actual amb canvis: `fix/prices-equals-controlleradvice-tests` (canvis empesos)
- Tests: 15 passant localment (unit + slice + SpringBoot tests)
- JaCoCo: cobertura OK
- Bloquejants: cap

---

## Canvis importants ja aplicats (resum)
- `PriceRepositoryPort` creat i injectat al servei (`ProductOffersServiceImpl`).
- `SpringDataPriceAdapter` (adapter infra) implementat i marcat com a bean; delegació cap a `SpringDataPricesRepository`.
- `ProductOffersControllerAdvice` actualitzat per mapar `PriceNotFoundException` → 404 i per utilitzar `ex.getReason()` de forma segura.
- Tests afegits/actualitzats: `ProductOffersControllerAdviceTest`, `SpringDataPriceAdapterTest`, `MapperProductOffersTest`, fixes a `ProductOffersServiceTests`.
- Placeholder repositories del paquet `domain` marcats `@Deprecated` (neteja progressiva).

---

## Tasques concretes (estat & següent acció)
- `Prices.java`
  - equals/hashCode amb `id` — DONE
  - `isEffectiveAt(...)` i `matches(...)` — TODO (PR #3)

- `price.domain.ports.PriceRepositoryPort` — DONE
- `price.infrastructure.persistence.SpringDataPriceAdapter` — DONE (unit test present)
- `SpringDataPricesRepository` (infra) — DONE
- `ProductOffersServiceImpl` — refactoritzat per usar el port i llençar `PriceNotFoundException` — DONE
- `ProductOffersControllerAdvice` — mapeig de noves excepcions i missatge segur — DONE
- `MapperProductOffers` — simplificació pendent — TODO
- Tests (unit / slice / integration) — cobertura millorada; afegir proves per `Prices` i proves d'integració per l'adapter — TODO
- Neteges finals (eliminar stubs / imports) — TODO

---

## Estratègia de proves (actualitzat)
- Afegir tests unit per `Prices.isEffectiveAt()` i `Prices.matches()` (PR #3).
- Afegir prova d'integració que demostri l'adapter delegant a Spring Data (pot ser un test de tipus @DataJpaTest o @SpringBootTest senzill).
- Revisar cobertura i afegir tests que cobreixin les branques noves del ControllerAdvice.

---

## Criteris d'acceptació per PR #2 (concrets)
- El servei depèn exclusivament de `PriceRepositoryPort` (no imports `JpaRepository` a `domain`).
- Hi ha un adapter a `infrastructure.persistence` amb unit tests.
- Les proves existents continuen passant i JaCoCo manté el llindar.
- Placeholder / interfícies legacy marcades com a deprecated o eliminades abans del merge final.

---

## Branch / PR naming i commits suggerits
- Branch actual (work-in-progress): `fix/prices-equals-controlleradvice-tests` (canvis empesos)
- Nova branch per PR #2 final: `refactor/ports-adapters-price-repository`
- Commits suggerits (petits, enfocats):
  - `refactor(port): add PriceRepositoryPort and SpringDataPriceAdapter`
  - `test: add SpringDataPriceAdapterTest + MapperProductOffersTest`
  - `refactor(service): depend on PriceRepositoryPort and throw PriceNotFoundException`

---

## Properes passes (prioritat alta)
1. Implementar `Prices.isEffectiveAt()` i `Prices.matches()` + tests (PR #3 entry) — estimat 0.5–1d.
2. Afegir prova d'integració per l'adapter (DataJpa / SpringBoot test) — estimat 0.5d.
3. Eliminar / netejar placeholders `domain` i actualitzar imports (pre-merge PR #2) — estimat 0.5d.
4. Simplificar `MapperProductOffers` i actualitzar tests — estimat 0.5d.
5. Obertura PR per revisar PR #2 i marcar per merge quan tot estigui net.

---

Si vols, procedeixo amb l'item 1 (implementar `Prices.isEffectiveAt()` i tests) ara mateix o obro el PR de revisió per PR #2; tria l'acció que prefereixis. 🔧
