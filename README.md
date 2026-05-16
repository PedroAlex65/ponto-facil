## 🧪 Estratégia de Testes Automatizados

O projeto adota uma abordagem rigorosa de testes automatizados para garantir a estabilidade das regras de negócio de controle de ponto:

* **Testes Unitários (Service):** Camada de negócio isolada e testada utilizando **JUnit 5** e **Mockito**. Adotamos a semântica **Given-When-Then** (BDD) para estruturar os cenários de teste, garantindo alta legibilidade. Os testes validam os fluxos de sucesso e o lançamento de exceções em regras críticas (como o bloqueio de registros duplicados de entrada/saída).
* **Testes de Integração (Controller):** Validação da camada HTTP utilizando **MockMvc** e a nova anotação `@MockitoBean` (Spring Boot 3.4+). Garantimos que os payloads JSON sejam convertidos corretamente, as URLs estejam bem mapeadas e os mecanismos de validação (`@Valid`) barrem dados corrompidos retornando o status `400 Bad Request`.
