# simulador-selic

./mvnw clean spring-boot:run

curl -w "\n" -iv  -H "Accept: application/json" \
    -H "Content-Type: application/json" -X POST "http://localhost:8080/simulador/selic/parcelas" \
    -d \
    '{
      "produto": {
        "codigo": 123,
        "nome": "Nome do Produto",
        "valor": 99.99
      },
      "condicaoPagamento": {
        "valorEntrada": 9.99,
        "qtdeParcelas": 10
      }
    }'
