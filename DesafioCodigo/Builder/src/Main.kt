/*
O Design Pattern "Builder" é uma técnica utilizada para criar objetos complexos passo a passo, separando o processo de construção da representação final do objeto. Isso ajuda a melhorar a legibilidade e a flexibilidade do código, especialmente quando você precisa criar objetos com muitos parâmetros ou configurações diferentes.

Neste desafio, buscando soluções para um problema de negócios em uma plataforma de e-commerce, é necessário melhorar a forma como os pedidos personalizados são tratados. Você tem a opção de implementar a solução utilizando o padrão Builder para criar pedidos de forma mais eficiente e organizada, ou seguir uma abordagem alternativa sem a necessidade de utilizar o padrão Builder.

Detalhamento da tarefa:

Capture o nome do cliente.
Solicite ao usuário a quantidade de produtos que deseja adicionar ao pedido.
Para cada produto, capture o nome, preço e quantidade.
Capture o endereço de entrega.
Calcule o total do pedido somando o preço de cada produto multiplicado pela quantidade.
Imprima os detalhes do pedido, incluindo os produtos, seus preços, quantidades, total a pagar e endereço de entrega.
Entrada
O programa deve receber as seguintes informações do usuário:

Nome do cliente (string);
Lista de produtos a serem incluídos no pedido (cada produto possui nome, preço e quantidade);
Endereço de entrega (string).
Saída
Após receber todas as informações do usuário, o programa deve criar um objeto de pedido personalizado usando o padrão Builder e imprimir os detalhes do pedido construído, incluindo o total a pagar.

Para este desafio, considere apenas uma casa decimal após a vírgula.

Exemplos
A tabela abaixo apresenta exemplos com alguns dados de entrada e suas respectivas saídas esperadas. Certifique-se de testar seu programa com esses exemplos e com outros casos possíveis.

Entrada	Saída
Ada
1
Chocolate
1.5
3
Arabaiana
Ada
1. Chocolate | 1.5 | 2
Total: 3.0
End: Arabaiana
Dante
1
Suco
2.5
2
Alto
Dante
1. Suco | 2.5 | 2
Total: 5.0
End: Alto

Sofia
1
Pipoca
4.5
3
Lagoa
Sofia
1. Pipoca | 4.5 | 3
Total: 13.50
End: Lagoa
 */

class Product(val name: String, val price: Double, val quantity: Int)

class CustomOrder private constructor(
    val customerName: String,
    val products: List<Product>,
    val total: Double,
    val deliveryAddress: String
) {
    /** Classe interna para "linkar" o Builder com a classe CustomOrder **/
    class Builder {
        private var customerName: String = ""
        private var products: MutableList<Product> = mutableListOf()
        private var deliveryAddress: String = ""

        fun setCustomerName(name: String) = apply { customerName = name }
        fun addProduct(product: Product) = apply { products.add(product) }
        fun setDeliveryAddress(address: String) = apply { deliveryAddress = address }

        fun build(): CustomOrder {
            // TODO("Implemente a lógica para calcular o Total do Pedido (a partir dos dados de Produtos).")
            // TODO("Instancie corretamente um CustomOrder, consolidando o Builder!")

            // Calcular o total
            var total = 0.0
            for (product in products) {
                total += product.price * product.quantity
            }

            // Criar a instância de CustomOrder
            // Como CustomOrder tem construtor privado, precisamos criar de outra forma
            return CustomOrder.createInstance(
                customerName,
                products.toList(),
                total,
                deliveryAddress
            )
        }
    }

    // Método companion para criar instâncias (fábrica)
    companion object {
        private fun createInstance(
            customerName: String,
            products: List<Product>,
            total: Double,
            deliveryAddress: String
        ): CustomOrder {
            return CustomOrder(customerName, products, total, deliveryAddress)
        }
    }

    fun printReceipt() {
        println("${this.customerName}")
        this.products.forEachIndexed { index, product ->
            println("${index + 1}. ${product.name} | ${product.price} | ${product.quantity}")
        }
        println("Total: ${"%.1f".format(this.total)}")
        println("End: ${this.deliveryAddress}")
    }
}

fun main() {
    val customerName = readLine() ?: ""

    val orderBuilder = CustomOrder.Builder().setCustomerName(customerName)

    val numProducts = readLine()?.toIntOrNull() ?: 0
    for (i in 1..numProducts) {
        val productName = readLine() ?: ""
        val productPrice = readLine()?.toDoubleOrNull() ?: 0.0
        val productQuantity = readLine()?.toIntOrNull() ?: 0

        orderBuilder.addProduct(Product(productName, productPrice, productQuantity))
    }

    val deliveryAddress = readLine() ?: ""

    val customOrder = orderBuilder.setDeliveryAddress(deliveryAddress).build()

    customOrder.printReceipt()
}