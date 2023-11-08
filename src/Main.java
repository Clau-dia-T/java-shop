//Importacoes
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    //Variaveis globais
    public static Scanner input_texto = new Scanner(System.in);
    public static Scanner input_numerico = new Scanner(System.in);
    public static ArrayList<String> produtos = new ArrayList<String>();
    public static ArrayList<Double> precos = new ArrayList<Double>();
    public static ArrayList<Double> vendas = new ArrayList<Double>();
    public static ArrayList<Integer> quantidades = new ArrayList<Integer>();
    public static DecimalFormat df = new DecimalFormat("0.00");
    public static final int TEMPO_CURTO = 350;
    public static final int TEMPO_LONGO = 1500;
    public static int id;
    public static double total_vendas;


    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        input_numerico = new Scanner(System.in);

        //Variaveis locais
        int opcao = 0;

        //Executar

        //_temp();     //ACTIVAR SE DESEJADO :)

        do {
            limpar();
            exibirMenu();
            opcao = input_numerico.nextInt();

            if (produtos.size() == 0 || precos.size() == 0){
                if(opcao < 0 || opcao > 7){
                    exibirMensagem(2);
                    carregueEnter();
                }
                else{
                    switch (opcao){
                        case 1: registar(); break;
                        case 0: sair(); break;
                        default:
                            System.out.println();
                            exibirMensagem(4);
                            carregueEnter(); break;
                    }
                }
            }

            else{
                switch (opcao){
                    case 1: registar(); break;
                    case 2: editar(); break;
                    case 3: pesquisarPreco(); break;
                    case 4: listarProdutos(); carregueEnter(); break;
                    case 5: registarVendas(); break;
                    case 6: listarVendas(); break;
                    case 7: apagar(); break;
                    case 0: sair(); break;
                    default: exibirMensagem(2); aguarde(TEMPO_LONGO); break;
                }
            }

        }while(opcao != 0);
    }

    //Funcoes
    public static void registar(){
        //Variaveis locais
        String produto_digitado;
        Boolean pesquisa = false;

        //Executar
        limpar();
        exibirSubMenu("Registar produtos");
        System.out.print("Digite o nome do produto a ser registado: ");
        produto_digitado = input_texto.nextLine();

        for(int i = 0; i<produtos.size(); i++){
            if(produto_digitado.equalsIgnoreCase(produtos.get(i))){
                pesquisa = true;
                exibirMensagem(3);
                System.out.println();
                carregueEnter();
                break;
            }
        }

        if(!pesquisa){
            produtos.add(produto_digitado);
            System.out.print("Digite o preço do produto: ");
            precos.add(input_numerico.nextDouble());
            quantidades.add(0);
            vendas.add(0.00);
            exibirMensagem(1);
            carregueEnter();
        }
    }
    public static void editar(){
        limpar();

        System.out.println();

        listarProdutos();

        exibirSubMenu("Editar produtos");

        System.out.print("Digite o código do produto a ser editado (de 0 até "+(produtos.size()-1)+"): ");
        id = input_numerico.nextInt();

        if(id >= 0 && id < produtos.size()){
            exibirProduto(id);
            System.out.print("\nDigite o nome do produto a ser registado: ");
            produtos.set(id, input_texto.nextLine());
            System.out.print("Digite o preço deste produto: ");
            precos.set(id, input_numerico.nextDouble());
            exibirMensagem(1);
            carregueEnter();
        }

        else{
            exibirMensagem(2);
            System.out.println();
            carregueEnter();
        }

    }
    public static void pesquisarPreco(){
        //Variaveis locais
        double max;
        double min;

        //Executar
        limpar();

        exibirSubMenu("Pesquisa de produtos por baliza de preços");
        System.out.print("Digite o valor mínimo de preço para a busca: ");
        min = input_numerico.nextDouble();
        System.out.print("Digite o valor máximo de preço para a busca: ");
        max = input_numerico.nextDouble();
        System.out.println();

        for(int i = 0; i < produtos.size(); i++){
            if (precos.get(i) >= min && precos.get(i) <= max){
                exibirProduto(i);
            }
        }
        System.out.println();
        carregueEnter();

    }
    public static void listarProdutos(){
        limpar();

        exibirSubMenu("Lista de produtos");

        for (int i = 0; i < produtos.size(); i++) {
            System.out.println("(" + (i) + ") - {" + produtos.get(i) + "} [" + df.format(precos.get(i)) + " €]");
        }

        System.out.println();

    }
    public static void registarVendas(){
        //Variaveis locais
        int quantidade;
        double venda;

        //Executar
        limpar();
        listarProdutos();

        exibirSubMenu("Registo de vendas");

        System.out.print("Digite o código do produto a ser editado de (0 até "+ (produtos.size()-1) +"): ");
        id = input_numerico.nextInt();

        if(id < 0 || id > (produtos.size()-1)){
            exibirMensagem(2);
            carregueEnter();
        }

        else{
            exibirProduto(id);

            System.out.print("\nDigite a quantidade de produto a vender: ");
            quantidade = input_numerico.nextInt();


            if(quantidade <= 0){
                exibirMensagem(2);
                carregueEnter();
            }

            else{

                quantidades.set(id, (quantidades.get(id)+quantidade));

                venda = precos.get(id) * quantidade;
                total_vendas += venda;

                System.out.println("\n(" + produtos.get(id) + ") x " + quantidade + " = [" + df.format(venda) + " €]");

                vendas.set(id, vendas.get(id)+venda);

                exibirMensagem(1);
                carregueEnter();
            }

        }
    }
    public static void listarVendas(){
        limpar();
        if (total_vendas == 0){
            exibirMensagem(5);
            carregueEnter();

        }
        else{
            exibirSubMenu("Lista de vendas");

            for (int i = 0; i < produtos.size(); i++) {
                if(quantidades.get(i) > 0){
                    System.out.println("(" + produtos.get(i) + ") x " + quantidades.get(i) + " = [" + df.format(vendas.get(i)) + " €]");
                }
            }

            System.out.println("\nValor total de vendas: (" + df.format(total_vendas) + " €)\n");
            carregueEnter();
        }
    }

    public static void apagar(){
        limpar();

        listarProdutos();
        exibirSubMenu("Eliminar produtos");
        System.out.print("Digite o ID do produto a eliminar (0 até "+ (produtos.size()-1)+"): ");
        id = input_numerico.nextInt();
        exibirProduto(id);
        if (id >= 0 && id < produtos.size()){
            total_vendas -= vendas.get(id);
            produtos.remove(id);
            precos.remove(id);
            vendas.remove(id);
            quantidades.remove(id);
            exibirMensagem(1);
            carregueEnter();
        }
        else{
            exibirMensagem(2);
            carregueEnter();
        }

    }
    public static void exibirMenu(){
        System.out.println("\n==== MERCADO ====\n");
        System.out.println("1- Registar produto.");
        System.out.println("2- Editar produto.");
        System.out.println("3- Pesquisar produto por baliza de preços.");
        System.out.println("4- Listar todos os produtos.\n");

        System.out.println("5- Registar venda.");
        System.out.println("6- Listar todas as vendas.\n");

        System.out.println("7- Apagar produtos.");
        System.out.println("0- Sair.\n");
        System.out.print("Opcao: ");
    }

    public static void exibirSubMenu(String txt){
        System.out.println("--- " + txt + " ---\n");
    }
    public static void exibirMensagem(int m){
        switch (m){
            case 1:
                System.out.println("\n--- Sucesso! ---\n"); break;
            case 2:
                System.out.println("\n--- Erro, valor inválido! ---"); break;
            case 3:
                System.out.println("\n--- Erro, produto já existente! ---"); break;
            case 4:
                System.out.println("--- Não existem produtos registados! ---"); break;
            case 5:
                System.out.println("--- Não existem vendas registadas! ---"); break;
            default:
                System.out.println("\n--- ??? ---\n"); break;
        }
    }
    public static void exibirProduto(int id){
        if(id >= 0 && id < produtos.size()){
            System.out.println("\n("+ id + ") - {" + produtos.get(id) + "} [" + df.format(precos.get(id)) +" €]");
        }
    }
    public static void sair(){
        limpar();
        System.out.print("A sair");
        aguarde(TEMPO_CURTO);
        System.out.print(".");
        aguarde(TEMPO_CURTO);
        System.out.print(".");
        aguarde(TEMPO_CURTO);
        System.out.print(".");
        aguarde(TEMPO_CURTO);
    }
    public static void limpar(){
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }
    public static void aguarde(int ms){
        try{
            Thread.sleep(ms);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void carregueEnter() {
        System.out.print("Carregue <enter> para continuar...");
        input_texto.nextLine();
    }
    public static void _temp(){
        produtos.add("Pão");
        precos.add(0.22);
        vendas.add(0.00);
        quantidades.add(0);

        produtos.add("Bolo");
        precos.add(9.99);
        vendas.add(0.00);
        quantidades.add(0);

        produtos.add("Leite");
        precos.add(2.49);
        vendas.add(0.00);
        quantidades.add(0);

    }
}