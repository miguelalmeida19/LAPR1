import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.io.*;
import java.nio.charset.StandardCharsets;    //para definir o encoding dos caracteres para UTF-8. isso permite fazer os ascii art
import java.util.Scanner;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class main {
    static Scanner ler = new Scanner(System.in).useLocale(Locale.ENGLISH);


    public static void limpeza() {
        File ficheiroDados = new File("dadosGrafico.txt");
        ficheiroDados.delete();

        File ficheiroScript = new File("script.gp");
        ficheiroScript.delete();
    }

    //************************************************************************************************
    //*******************************     MENSAGENS/INTERFACE     ************************************
    //************************************************************************************************

    public static void mensagemErro(int valor) {
        switch (valor) {
            case -2:
                System.out.println("\n**********************************************************************************");
                System.out.println("\n           -> Por favor verifique se os parametros estão corretos <-          ");
                mensagemUtilizacaoPrograma();
                break;

            case -1:
                System.out.println("\n**********************************************************************************");
                System.out.println("\n-> Ficheiro de input inválido. Por favor, verifique a escrita e tente novamente <-\n");
                System.out.println("**********************************************************************************");
                break;

            case 0:
                mensagemUtilizacaoPrograma();
        }
    }

    public static void mensagemUtilizacaoPrograma() {

        System.out.println("\n*********************** Modo de utilização do programa ***************************");
        System.out.println("\nUtilização do programa: ");
        System.out.println("\n  -Modo interativo:");
        System.out.println("      java -jar programa.jar                                Executa o programa no modo 100% interativo.");
        System.out.println("                                                            O utilizador terá que introduzir todos os dados manualmente");
        System.out.println("      java -jar programa.jar -n ficheiro_Input.txt          Executa o programa no modo interativo mas faz a leitura dos");
        System.out.println("                                                            Dados de um ficheiro de input.");
        System.out.println("  -Modo não interativo:");
        System.out.println("      java -jar programa.jar [-t X -g Y -e -v -r] input.txt output.txt");
        System.out.println("\nParametros:");
        System.out.println("           -t VALOR                                         Numero de gerações a estimar");
        System.out.println("           -g VALOR                                         Identifica o formato dos ficheiros gerados pelo gnuplot");
        System.out.println("                                                            Valores permitidos: 1,2,3");
        System.out.println("           -e                                               Calcular o valor e o vetor pr´oprio");
        System.out.println("           -v                                               Calcular a dimensão da populaçãao a cada geraçãao");
        System.out.println("           -r                                               Calcular a a variaçãao da populaçãao entre gerações");
        System.out.println("           --help                                           Mostra a ajuda");
    }

    public static int menugraficos() {

        //indice(Menu onde o utilizador escolhe quais os gráfico que desja visualizar)
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.println("Qual dos gráficos seguintes deseja visualizar?");
        System.out.println("Indique uma das seguintes opções:");
        System.out.println("1 -    Gráfico População    || 2 -    Gráfico Variação   ");
        System.out.println("3 - Gráfico da Distribuição || 4 - Gráfico da Normalizada");
        System.out.println("5 - Sair");
        System.out.print("\nResposta: ");

        int numero = ler.nextInt();

        //Se o utilizador escrever um número diferente de 1, 2, 3, 4, 5, neste caso, o programa volta a pedir o número

        while (numero != 1 & numero != 2 & numero != 3 & numero != 4 & numero != 5) {

            System.out.println("\n*********************** Número inválido, digite de novo *************************\n");
            System.out.println("Qual dos gráficos seguintes deseja visualizar?");
            System.out.println("Indique uma das seguintes opções:");
            System.out.println("1 -    Gráfico População    || 2 -    Gráfico Variação   ");
            System.out.println("3 - Gráfico da Distribuição || 4 - Gráfico da Normalizada");
            System.out.println("5 - Saír");
            System.out.print("\nResposta: ");
            numero = ler.nextInt();
        }
        return numero;
    }

    public static void mensagemErroFicheiro(int valor) {
        // 0 = tudo bem
        // 1 = erro na estrutura do ficheiro
        // 2 = tem demasiadas casas decimais
        switch (valor) {
            case 1:
                System.out.println("\n*********************************************************************************************");
                System.out.println("\n           -> Erro na estrutura do ficheiro de input. Verifique se está correto <-          ");
                System.out.println("\n*********************************************************************************************");
                break;

            case 2:
                System.out.println("\n*********************************************************************************************");
                System.out.println("\n->  Os valores introduzidos no ficheiro de input têm um numero inválido de casas decimais. <-");
                System.out.println("\n                     Numero de casas decimais permitidas: 0 ou 2\n");
                System.out.println("**********************************************************************************************");
                break;
        }
    }

    public static int menuMainInterativo() {

        //indice(Menu onde o utilizador escolhe quais os gráfico que desja visualizar)
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.println("Indique uma das seguintes opções:");
        System.out.println("1 - Ler novo ficheiro       ||  2 - Graficos");
        System.out.println("3 - Introduzir novos dados  ||  4 - Sair");
        System.out.print("\nResposta: ");
        int numero = ler.nextInt();

        //Se o utilizador escrever um número diferente de 1, 2, 3, neste caso, o programa volta a pedir o número

        while (numero != 1 & numero != 2 & numero != 3 & numero != 4) {

            System.out.println("\n********************** Número inválido, digite de novo *************************\n");
            System.out.println("Indique uma das seguintes opções:");
            System.out.println("1 - Ler novo ficheiro       ||  2 - Graficos");
            System.out.println("3 - Introduzir novos dados  ||  4 - Sair");
            System.out.print("\nResposta: ");
            numero = ler.nextInt();
        }
        return numero;
    }

    public static String lerNovoFicheiro(){
        System.out.println("Qual é o nome do novo ficheiro de entrada?");
        System.out.print("\nNome: ");
        String ficheiro = ler.nextLine();
        if (!ficheiro.contains(".txt")) {
            ficheiro = ficheiro + ".txt";
        }
        return ficheiro;
    }

    public static String menuNovoFicheiro() {
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

        //Nome do ficheiro de entrada
        ler.nextLine();
        String ficheiro = lerNovoFicheiro();
        File ficheiroCheck = new File(ficheiro);
        while(!ficheiroCheck.isFile()){
            System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
            System.out.println("                              Ficheiro Inválido");
            System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
            ficheiro = lerNovoFicheiro();
            ficheiroCheck = new File(ficheiro);
        }
        return ficheiro;
    }

    //************************************************************************************************
    //**************************************MODO 100% INTERATIVO**************************************
    //************************************************************************************************

    public static String nomePopulacao(String[] parametros){
        String[] caminho = parametros[5].split(Pattern.quote("\\"));
        if(caminho.length == 1){
            return caminho[0].split(Pattern.quote("."))[0];
        }else{
            return caminho[caminho.length -1].split(Pattern.quote("."))[caminho[caminho.length -1].split(Pattern.quote(".")).length -2];

        }

    }

    public static String[][] interacao(String[] parametros, int modo) throws FileNotFoundException {

        //Apresentação ao utilizador do programa

        String[][] array = new String[3][];
        int numgeracao;

        //Passagem para cada um dos módulos do array e do numero de gerações
        //100% INTERATIVO
        if (modo == 0) {
            array = numerogeracoes();
            numgeracao = array[0].length;
            estimargeracoes(parametros);

            geracoes(array, numgeracao);
            taxasobrevivencia(array, numgeracao);
            taxafecundidade(array, numgeracao);
            ler.nextLine();
            nomeEspecie(parametros);
            e(parametros);
            v(parametros);
            r(parametros);

            //SEMI INTERATIVO
        } else if (modo == 1) {

            estimargeracoes(parametros);
            ler.nextLine();
            e(parametros);
            v(parametros);
            r(parametros);

            array = LerDoFicheiro(parametros[5]);
        }
        return array;
    }

    public static void nomeEspecie(String[] parametros){
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.print("Digite o nome da espécie em estudo: ");
        String especie = ler.nextLine();
        String letra;

        do {
            System.out.println("\n********************************* CONFIRMAÇÃO **********************************\n");
            System.out.println("Digitou que o nome da espécie é: '" + especie + "'");
            System.out.println("Confirma? Indique uma das seguintes opções:");
            System.out.println("S - Sim, confirmo! || N - Não, desejo alterar o nome!");
            System.out.print("\nResposta: ");
            letra = ler.nextLine().toUpperCase(Locale.ROOT);

            while (!letra.equals("S") & !letra.equals("N")) {

                System.out.println("\n*********************** Letra inválida, digite de novo *************************\n");
                System.out.println("Digitou que o nome da espécie é: '" + especie + "'");
                System.out.println("Confirma? Indique uma das seguintes opções:");
                System.out.println("S - Sim, confirmo! || N - Não, desejo alterar o nome!");
                System.out.print("\nResposta: ");
                letra = ler.nextLine().toUpperCase(Locale.ROOT);
            }

            if (letra.equals("N")) {
                System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
                System.out.print("Digite o nome da espécie em estudo: ");
                especie = ler.nextLine();
            }
        } while (!letra.equals("S"));

        parametros[5]=especie;
    }

    public static String[][] numerogeracoes() {

        System.out.print("\nIndique o número de gerações que vai introduzir: ");
        int numgeracao = ler.nextInt();

        //Se o utilizador escrever um valor inválido, negativo neste caso, o programa volta a pedir o valor

        while (numgeracao <= 0) {

            System.out.println("\n*********************** Valor inválido, digite de novo *************************\n");

            System.out.print("Indique o número de gerações da espécie em questão: ");
            numgeracao = ler.nextInt();
        }
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        //Criação do array que armazena os valores todos da matriz

        return new String[3][numgeracao];
    }

    public static void estimargeracoes(String[] parametros) {

        System.out.print("\nIndique o número de gerações a estimar: ");

        int anos = ler.nextInt();

        //Se o utilizador escrever um valor inválido, diferente de 1, 2, ou 3 neste caso, o programa volta a pedir o valor

        while (anos <= 0) {

            System.out.println("\n*********************** Valor inválido, digite de novo *************************\n");
            System.out.print("Indique o número de gerações a estimar: ");
            anos = ler.nextInt();
        }
        parametros[0] = String.valueOf(anos);
    }

    public static void e(String[] parametros) {

        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

        //E(Obrigatoriedade de calcular o valor e o velor próprio)
        System.out.println("Deseja calcular o valor e o vetor próprio?");
        System.out.println("Indique uma das seguintes opções:");
        System.out.println("S - Sim, desejo calcular! || N - Não, não desejo calcular!");
        System.out.print("\nResposta: ");

        String letra = ler.nextLine().toUpperCase(Locale.ROOT);

        //Se o utilizador escrever uma letra diferente de S e N, neste caso, o programa volta a pedir a letra

        while (!letra.equals("S") & !letra.equals("N")) {

            System.out.println("\n*********************** Letra inválida, digite de novo *************************\n");
            System.out.println("Deseja calcular o valor e o vetor próprio?");
            System.out.println("Indique uma das seguintes opções:");
            System.out.println("S - Sim, desejo calcular! || N - Não, não desejo calcular");
            System.out.print("\nResposta: ");
            letra = ler.nextLine().toUpperCase(Locale.ROOT);
        }
        parametros[2] = letra;

        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
    }

    public static void v(String[] parametros) {

        //V(Obrigatoriedade de calcular a dimensão da população a cada geração)
        System.out.println("Deseja calcular a dimensão da população a cada geração?");
        System.out.println("Indique uma das seguintes opções:");
        System.out.println("S - Sim! || N - Não!");
        System.out.print("\nResposta: ");

        String letra = ler.nextLine().toUpperCase(Locale.ROOT);

        //Se o utilizador escrever uma letra diferente de S e N, neste caso, o programa volta a pedir a letra

        while (!letra.equals("S") & !letra.equals("N")) {

            System.out.println("\n*********************** Letra inválida, digite de novo *************************\n");
            System.out.println("Deseja calcular a dimensão da população a cada geração?");
            System.out.println("Indique uma das seguintes opções:");
            System.out.println("S - Sim! || N - Não!");
            System.out.print("\nResposta: ");
            letra = ler.nextLine().toUpperCase(Locale.ROOT);
        }
        parametros[3] = letra;

        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
    }

    public static void r(String[] parametros) {

        //R(Obrigatoriedade de calcular a variação da população entre gerações)
        System.out.println("Deseja calcular a variação da população entre gerações?");
        System.out.println("Indique uma das seguintes opções:");
        System.out.println("S - Sim! || N - Não!");
        System.out.print("\nResposta: ");

        String letra = ler.nextLine().toUpperCase(Locale.ROOT);

        //Se o utilizador escrever uma letra diferente de S e N, neste caso, o programa volta a pedir a letra

        while (!letra.equals("S") & !letra.equals("N")) {

            System.out.println("\n*********************** Letra inválida, digite de novo *************************\n");
            System.out.println("Deseja calcular a variação da população entre gerações?");
            System.out.println("Indique uma das seguintes opções:");
            System.out.println("S - Sim! || N - Não!");
            System.out.print("\nResposta: ");
            letra = ler.nextLine().toUpperCase(Locale.ROOT);
        }
        parametros[4] = letra;
    }

    public static void g(String[] parametros) {

        //G (Indica o formato do ficheiro dos gráficos gerado pelo gnuplot)
        System.out.println();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println();
        System.out.println("Qual o formato do ficheiro de gráfico gerado pelo gnuplot que deseja?");
        System.out.println("Indique uma das seguintes opções:");
        System.out.println("1 - Formato PNG || 2 - Formato TXT || 3 - Formato EPS");
        System.out.print("\nDesejo o formato: ");

        int formato = ler.nextInt();

        //Se o utilizador escrever um valor inválido, diferente de 1, 2, ou 3 neste caso, o programa volta a pedir o valor

        while (formato != 1 & formato != 2 & formato != 3) {

            System.out.println("\n*********************** Valor inválido, digite de novo *************************\n");
            System.out.println("Qual o formato do ficheiro de gráfico gerado pelo gnuplot que deseja?");
            System.out.println("Indique uma das seguintes opções:");
            System.out.println("1 - Formato PNG || 2 - Formato TXT || 3 - Formato EPS");
            System.out.print("\nDesejo o formato: ");
            formato = ler.nextInt();
        }
        parametros[1] = String.valueOf(formato);
        ler.nextLine();
    }

    public static void geracoes(String[][] array, int numgeracao) {

        int quantidade;
        //quantidade - Representa o número de individuos de cada geração

        //Quantos individuos têm cada uma das gerações
        for (int i = 0; i < numgeracao; i++) {

            System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

            System.out.print("Indique quantos individuos existem na " + (i + 1) + "ª geração: ");

            quantidade = ler.nextInt();

            //Se o utilizador escrever um valor inválido,negativo  neste caso, o programa volta a pedir o valor
            while (quantidade < 0) {

                System.out.println("\n+++++++++++++++++++++++++ Valor inválido, digite de novo ++++++++++++++++++++++++++\n");
                System.out.print("Indique quantos individuos existem na " + (i + 1) + "ª geração: ");
                quantidade = ler.nextInt();
                //Preenchimento do array
                array[0][i] = String.valueOf(quantidade);
            }
            //Preenchimento do array
            array[0][i] = String.valueOf(quantidade);
        }
    }

    public static void taxasobrevivencia(String[][] array, int numgeracao) {

        double quantidade;
        //quantidade - Representa a taxa de sobrevivência de cada geração

        //Qual a taxa de mortalidade de cada uma das gerações
        for (int i = 0; i < numgeracao - 1; i++) {

            System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

            System.out.print("Indique a taxa de sobrevivência da geração " + (i + 1) + ": ");

            quantidade = ler.nextDouble();

            //Se o utilizador escrever um valor inválido, negativo neste caso, o programa volta a pedir o valor
            while (quantidade < 0) {

                System.out.println("\n++++++++++++++++++++++++ Valor inválido, digite de novo ++++++++++++++++++++++++\n");
                System.out.print("Indique a taxa de sobrevivência da geração " + (i + 1) + ": ");
                quantidade = ler.nextDouble();
                //Preenchimento do array
                array[1][i] = String.valueOf(quantidade);
            }
            //Preenchimento do array
            array[1][i] = String.valueOf(quantidade);
        }
    }

    public static void taxafecundidade(String[][] array, int numgeracao) {

        double quantidade;
        //quantidade - Representa a taxa de fecundidade de cada geração

        //Qual a taxa de fecundidade de cada uma das gerações
        for (int i = 0; i < numgeracao; i++) {

            System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

            System.out.print("Indique a taxa de fecundidade da geração " + (i + 1) + ": ");

            quantidade = ler.nextDouble();

            //Se o utilizador escrever um valor inválido, negativo neste caso, o programa volta a pedir o valor
            while (quantidade < 0) {

                System.out.println("\n++++++++++++++++++++++++ Valor inválido, digite de novo ++++++++++++++++++++++++\n");
                System.out.print("Indique a taxa de fecundidade da geração " + (i + 1) + ": ");
                quantidade = ler.nextDouble();
                //Preenchimento do array
                array[2][i] = String.valueOf(quantidade);
            }
            //Preenchimento do array
            array[2][i] = String.valueOf(quantidade);
        }
    }

    //************************************************************************************************
    //**************************************MODO NÃO INTERATIVO***************************************
    //************************************************************************************************

    public static int procurar(String[] args, String valor) {
        /* Este método procura por uma string num array

            params: args - array onde procurar
                    valor - string a procurar

            return: retorna a posicao do elemento no array que é igual á string a procurar.
                    Caso não seja encontrado, retorna um valor negativo (-20)

         */
        int posicao = -20;
        for (int n = 0; n < args.length; n++) {
            if (args[n].equals(valor)) {
                return n;
            }
        }
        return posicao;
    }

    public static int tamanhoMatriz(String ficheiro) throws FileNotFoundException {
        /* Este método obtem o numero de colunas da matriz a ser criada no método de ler o ficheiro. Essa matriz irá conter todos os valores do ficheiro de input.

           param: String primeiraLinha - primeira linha do ficheiro
           Return: tamanho - numero de colunas da matriz
         */

        Scanner scanner = new Scanner(new File(ficheiro));
        String linha = scanner.nextLine();

        while(!linha.contains("x") && scanner.hasNextLine()){
            linha = scanner.nextLine();
        }
        String[] valoreslinha = linha.split(" ");
        return valoreslinha.length;
    }

    public static void preencherMatrizDadosInput(String[][] matrizDados, String linha) {
        /* Este método trata as linhas obtidas do ficheiro, removendo todos os caracteres desnecessários.

           params: matrizDados - matriz com os dados necessários para os calculos. A matriz é passada por referencia e vai ser preenchida.
                   linha -  linha lida do ficheiro
                   numeroLinha - necessário para preencher a linha certa da matrizDados
         */
        int numeroLinha;
        final int x = 0;
        final int s = 1;
        final int f = 2;
        linha = linha.replace(",", "");
        String[] valores = linha.split(" ");
        if (linha.contains("x")) {
            for (int n = 0; n < matrizDados[1].length; n++) {
                numeroLinha = x;
                matrizDados[numeroLinha][n] = valores[n].split("=")[1];
            }
        } else if (linha.contains("s")) {
            for (int n = 0; n < matrizDados[1].length -1; n++) {
                numeroLinha = s;
                matrizDados[numeroLinha][n] = valores[n].split("=")[1];
            }
        } else {
            for (int n = 0; n < matrizDados[1].length; n++) {
                numeroLinha = f;
                matrizDados[numeroLinha][n] = valores[n].split("=")[1];
            }
        }
    }

    public static int verificarParcela(String valor) {
        //verifica um valor do ficheiro
        //ex: x00=10
        // return 0 - td bem
        //        1 - temos problemas no nº de casas decimais....
        String[] casasDecimais = valor.split("\\.");
        if (casasDecimais.length > 1 && valor.split("\\.").length == 2 ) {
            if (valor.split("\\.")[1].length() != 2) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static void verificarFicheiroInput(String linha) {
        /* Este método verifica se o ficheiro de input está com os dados corretos
            Se o x00 e o x01 não estiverem por ordem sai e mostra uma mensagem de erro
         */
        //s0=0.50, s1=0.80, s2=0.50, s3=0.30

        //x00=20, x01=10, x02=40, x03=30. x04=20

        int tudoOk = 0;  // 0 = tudo bem
        // 1 = erro na estrutura do ficheiro
        //2 = tem demasiadas casas decimais

        //verificar se a estrutura do ficheiro é seguida
        int contador = 0;
        if (linha.contains("=")) {
            String[] valores = linha.split(", ");
            int valorAnterior = Integer.parseInt(valores[0].split("=")[0].substring(1));
            for (int n = 0; n < valores.length; n++) {
                if (verificarParcela(valores[n].split("=")[1]) == 1) {
                    tudoOk = 2;
                }
                if (n != 0) {
                    if (Integer.parseInt(valores[n].split("=")[0].substring(1)) <= valorAnterior || Integer.parseInt(valores[n].split("=")[0].substring(1)) != contador) {
                        tudoOk = 1;
                    }
                }
                contador++;
            }
        } else {
            tudoOk = 1;
        }
        if (tudoOk != 0) {
            mensagemErroFicheiro(tudoOk);
            System.exit(1);
        }
    }

    public static String[][] LerDoFicheiro(String ficheiro) throws FileNotFoundException {         //preenche todos os espaços da matriz com os dados do ficheiro
        /* Este método lê os dados do ficheiro

           params: ficheiro - noome do ficheiro a abrir
           return dados - matriz com os dados necessários para o calculo. os dados já estão tratados.
         */

        Scanner read = new Scanner(new File(ficheiro));

        int colunas = tamanhoMatriz(ficheiro);
        String linha = read.nextLine();
        String[][] dados;

        dados = new String[3][colunas];

        verificarFicheiroInput(linha);
        preencherMatrizDadosInput(dados, linha);
        while (read.hasNextLine()) {
            linha = read.nextLine();
            if (linha.contains("x") || linha.contains("s") || linha.contains("f")) {
                verificarFicheiroInput(linha);
                preencherMatrizDadosInput(dados, linha);
            }
        }
        return dados;
    }

    public static void naoInterativo(String[] args, String[] dados) {
        /* Método principal para o modo não interativo.

           params: args - argumentos introduzidos na linha de comando pelo user.
                   dados - Matriz que irá conter os dados para os cálculos.
         */

        dados[0] = args[procurar(args, "-t") + 1];
        dados[1] = args[procurar(args, "-g") + 1];
        dados[dados.length - 1] = args[args.length - 1];
        dados[dados.length - 2] = args[args.length - 2];
        String[] parametros = {"-e", "-v", "-r"};

        for (int n = 2; n < dados.length - 2; n++) {     //como são parametros que não teem valores associados é só vberificar se existem
            int m = procurar(args, parametros[n - 2]);
            if (m < 0) {
                dados[n] = "FALSE";
            } else {
                dados[n] = "TRUE";
            }
        }
    }

    public static int verificacaoNumeroParametros(String[] args) {
        /* Este método verifica se o utilizador introduziu todos os parametros necessários á execução do programa no modo
           não interativo

           param: args - argumentos passados para o programa
           return:   -2 se faltarem argumnentos
                     -1 se ficheiro input for invalido
                      0 se for introduzido o --help
                      1 se estiver td ok
         */
        // -t 2 -g ola input output
        if ((args.length < 6 && args.length > 2)) {
            return -2;
        } else if (args.length > 0 && args[0].equals("--help")) {
            return 0;
        } else if (args.length == 0) {
            return 1;
        }
        if (args.length <= 2) {
            File f = new File(args[1]);
            if (f.exists() && !f.isDirectory()) {                //não pode ser uma pasta
                return 1;
            } else {
                return -1;
            }
        }

        // vamos checar se o penultimo parametro é um ficheiro que existe
        File f = new File(args[args.length - 2]);
        if (f.exists() && !f.isDirectory()) {                //não pode ser uma pasta
            int a = procurar(args, "-t");
            int b = procurar(args, "-g");
            if (a < 0 || b < 0) {
                return -2;
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }

    public static String[][] modoExecucao(String[] args, String[] parametros) throws FileNotFoundException {
        /* Este método deteta o modo de execussão do programa baseado no numero de parametros que são passados.

           param: args - argumentos passados
                  parametros - matriz que vai receber os parametros que o utilizador passou
           return: Retorna a matriz com os dados (provenientes do ficheiro ou do user) necessários para os calculos

         */
        if (args.length <= 2) {
            System.out.println("\n****************** SEJA MUITO BEM VINDO AO PROGRAMA DOS RIJOS ******************\n");
            System.out.println("Olá Sr.Biologo, este programa é destinado à modelação da evolução de uma Espécie\n");
            System.out.println("********************************************************************************");
            //return interacao(args);
            if (args.length == 0) {
                // modo totalmente interativo
                return interacao(parametros, 0);
            } else {
                // modo semi interativo
                parametros[5] = args[1]; // preencher a parte do ficheiro de input da matriz para que o método possa ler a matriz
                return interacao(parametros, 1);
            }

        } else {
            interacao(parametros, 2);
            System.out.println();
            naoInterativo(args, parametros);
            return LerDoFicheiro(parametros[5]);
        }
    }

    //************************************************************************************************
    //**************************************     CALCULOS      ***************************************
    //************************************************************************************************

    public static String[][] Geracoes(String[][] input) {
        /*
        Este método lê apenas a primeira linha da matriz input, que contem a qnt de elementos de cada geração

        param: input
        return: Ni
         */
        //System.out.println(input[0].length);
        String[][] Ni = new String[input[0].length][1];
        for (int i = 0; i < input[0].length; i++) {
            Ni[i][0] = input[0][i];
        }
        return Ni;
    }

    public static String[][] MatrizLeslie(String[][] input) {
        /*
        Este método preenche uma matriz quadrada, a matriz de Leslie, através do input.

        param: input
        return: matrizleslie
         */
        String[] S = Survival(input);
        String[] F = Fecundidade(input);

        String[][] matrizleslie = new String[F.length][F.length];
        //fecundidade
        preencherFecundidade(matrizleslie, F);
        //survival
        preencherSurvival(matrizleslie, S);
        //final
        preenchedorDeNulls(matrizleslie);
        //escrever(Conversor(matrizleslie));
        return matrizleslie;
    }

    public static int contadorSurvival(String[][] input) {
        /*
        Este método vai à matriz input e conta os valores survival para posteriormente ter o tamanho da matriz survival.

        param: input
        return: contadordeS
         */
        int contadordeS = 0;
        for (int i = 0; i < input[0].length; i++) {
            if (Double.parseDouble(input[1][i]) > 0) {
                contadordeS++;
            }
        }
        return contadordeS;
    }

    public static String[] Survival(String[][] input) {
        /*
        Este método preenche uma matriz que irá preencher uma matrizlinha com os dados de survival do input

        param: input
        return: S
         */
        String[] S = new String[contadorSurvival(input)];
        for (int i = 0; i < S.length; i++) {
            S[i] = input[1][i];
        }
        return S;
    }

    public static String[] Fecundidade(String[][] input) {
        /*
        Este método preenche uma matriz que irá preencher uma matrizlinha com os dados de Fecundidade do input

        param: input
        return: F
         */
        String[] F = new String[Geracoes(input).length];
        for (int i = 0; i < Geracoes(input).length; i++) {
            F[i] = input[2][i];
        }
        return F;
    }

    public static void preencherSurvival(String[][] matrizleslie, String[] S) {
        /*
        Este método preenche a matriz leslie com os dados da matriz survival, em diagonal

        param: matrizleslie, S, F
         */
        int k = 1;
        for (int f = 0; f < S.length; f++) {
            matrizleslie[k][f] = S[f];
            k++;
        }
    }

    public static void preencherFecundidade(String[][] matrizleslie, String[] F) {
        /*
        Este método preenche a matriz leslie com os dados da matriz fecundidade

        param: matrizleslie, F
         */
        for (int i = 0; i < F.length; i++) {
            matrizleslie[0][i] = F[i];
        }
    }

    public static void preenchedorDeNulls(String[][] matrizleslie) {
        /*
        Este método evita que as matrizes String contenham nulls, capazes de fazer com que haja um erro. Para isso, preenche a matriz vazia com 0 em vez de nulls.

        param: matrizleslie
         */
        for (int i = 0; i < matrizleslie.length; i++) {
            for (int f = 0; f < matrizleslie[0].length; f++) {
                if (matrizleslie[i][f] == null) {
                    matrizleslie[i][f] = String.valueOf(0);
                }
            }
        }
    }

    public static void preenchedorDeNullsParaDoubles(Double[][] matrizacorrigir) {
        /*
        Este método evita que as matrizes String contenham nulls, capazes de fazer com que haja um erro. Para isso, preenche a matriz vazia com 0 em vez de nulls.

        param: matrizacorrigir
         */
        for (int i = 0; i < matrizacorrigir.length; i++) {
            for (int f = 0; f < matrizacorrigir[0].length; f++) {
                if (matrizacorrigir[i][f] == null) {
                    matrizacorrigir[i][f] = (double) 0;
                }
            }
        }
    }

    public static Double[][] multiplicacaoDeMatrizesQuadradas(Double[][] matriz1, Double[][] matriz2) {
        /*
        Este método recebe duas matrizes quadradas e faz a sua multiplicação

        param: matriz1, matriz2
        return : resultadofinal
         */
        Double[][] resultadofinal = new Double[matriz1.length][matriz1.length];
        for (int i = 0; i < matriz1.length; i++) {
            for (int j = 0; j < matriz1.length; j++) {
                resultadofinal[i][j] = (double) 0;
                for (int k = 0; k < matriz1.length; k++) {
                    resultadofinal[i][j] += (matriz1[i][k] * matriz2[k][j]);
                }
            }
        }
        return resultadofinal;
    }

    public static Double[][] Conversor(String[][] matrizString) {
        /*
        Este método permite converter matrizes quadradas de double para String

        param: matrizString
        return: matrizConvertida
         */
        //transformar string em double
        Double[][] matrizConvertida = new Double[matrizString.length][matrizString.length];
        for (int i = 0; i < matrizString.length; i++) {
            for (int j = 0; j < matrizString[0].length; j++) {
                matrizConvertida[i][j] = Double.parseDouble(matrizString[i][j]);
            }
        }
        return matrizConvertida;
    }

    public static Double[][] multiplicacaoDeMatrizesComExpoente(Double[][] matriz1, Double[][] matriz2, int expoente, String[][] Ni) {
        /*
        Este método recebe um expoente e se for 1, escreve a matriz sem a multiplicar, se for >2, faz as multiplicações sucessivas conforme o expoente

        param: matriz1, matriz2, expoente, Ni
        return: matriz1 ou Conversor(Ni) ou resultadofinal
         */

        if (expoente == 1) {
            return matriz1;
        } else if (expoente == 0) {
            return Conversor(Ni);
        } else {
            Double[][] resultadofinal = multiplicacaoDeMatrizesQuadradas(matriz1, matriz2);
            for (int i = 1; i < expoente - 1; i++) {
                resultadofinal = multiplicacaoDeMatrizesQuadradas(resultadofinal, matriz2);
            }
            return resultadofinal;
        }
    }

    public static Double[][] multiplicacaoporN(Double[][] matrizComExpoenteFinal, String[][] Geracoes) {
        /*
        Este método recebe a matriz que foi elevada a um expoente e multiplica pela matriz da população de cada geração

        param: matrizcComExpoenteFinal, Geracoes
        return: matriz1 ou Conversor(Ni) ou resultadofinal
         */
        Double[][] GeracoesConvertidas = Conversor(Geracoes);
        Double[][] ResultadoFinal = new Double[GeracoesConvertidas.length][1];
        preenchedorDeNullsParaDoubles(ResultadoFinal);
        for (int i = 0; i < matrizComExpoenteFinal.length; i++) {
            for (int j = 0; j < matrizComExpoenteFinal.length; j++) {
                //System.out.println("Estou na linha " + i + " coluna " + j);
                ResultadoFinal[i][0] += GeracoesConvertidas[j][0] * matrizComExpoenteFinal[i][j];
            }
        }
        return ResultadoFinal;
    }

    public static Double[][] FormulaLeslie(String[][] input, int t) {
        /*
        Este método compila todos os métodos que possibilitam a execução da fórmula de Leslie

        param: input, t
        return: L ou multiplicacaoporN(L, Ni)
         */

        String[][] Ni = Geracoes(input);
        Double[][] Leslieconvertida = Conversor(MatrizLeslie(input));
        Double[][] L = multiplicacaoDeMatrizesComExpoente(Leslieconvertida, Leslieconvertida, t, Ni);

        if (L[0][0] == Double.parseDouble(Ni[0][0])) {
            //escrever(Conversor(Ni));
            return L;
        } else {
            return multiplicacaoporN(L, Ni);
        }
    }

    public static double soma(Double[][] matrizpopulacaoDiferenciada) {
        /*
        Este método recebe o resultado da fórmula de Leslie e soma todos os valores

        param: matrizpopulacaoDiferenciada
        return: soma
         */
        double soma = 0;
        for (int i = 0; i < matrizpopulacaoDiferenciada.length; i++) {
            soma += matrizpopulacaoDiferenciada[i][0];
        }
        return soma;
    }

    public static Double[][] variacaoPop(Double[][] PopulacaoTotal) {

        Double[][] variacaoPop = new Double[1][PopulacaoTotal[0].length - 1];
        for (int i = 0; i < PopulacaoTotal[0].length - 1; i++) {
            variacaoPop[0][i] = PopulacaoTotal[0][i + 1] / PopulacaoTotal[0][i];
        }
        return variacaoPop;
    }

    public static Double[][] populacaoaCadaGeracao(Double[][] distribuicaoPopulacao) {

        Double[][] PopulacaoTotal = new Double[1][distribuicaoPopulacao[0].length];
        Double[][] colunaAtual = new Double[distribuicaoPopulacao.length][1];
        double soma;
        for (int j = 0; j < distribuicaoPopulacao[0].length; j++) {
            for (int i = 0; i < distribuicaoPopulacao.length; i++) {
                colunaAtual[i][0] = distribuicaoPopulacao[i][j];
            }
            soma = soma(colunaAtual);
            PopulacaoTotal[0][j] = soma;
        }
        return PopulacaoTotal;
    }

    public static Double[][] distribuicaoPopulacao(String[][] input, String[] parametros) {

        int t = Integer.parseInt(parametros[0]);
        Double[][] Distribuicao = new Double[input[0].length][t + 1];
        preenchedorDeNullsParaDoubles(Distribuicao);
        Double[][] MatrizColuna = new Double[input[0].length][1];
        for (int i = 0; i <= t; i++) {
            for (int j = 0; j < MatrizColuna.length; j++) {
                MatrizColuna = FormulaLeslie(input, t - (t - i));
                Distribuicao[j][i] = MatrizColuna[j][0];
            }
        }
        return Distribuicao;
    }

    public static Double[][] distribuicaoNormalizada(Double[][] distribuicaoPopulacao) {

        Double[][] colunaAtual = new Double[distribuicaoPopulacao.length][1];
        Double[][] distribuicaoNormalizada = new Double[distribuicaoPopulacao.length][distribuicaoPopulacao[0].length];
        double soma;
        for (int j = 0; j < distribuicaoPopulacao[0].length; j++) {
            for (int i = 0; i < distribuicaoPopulacao.length; i++) {
                colunaAtual[i][0] = distribuicaoPopulacao[i][j];
            }
            soma = soma(colunaAtual);
            for (int m = 0; m < distribuicaoPopulacao.length; m++) {
                distribuicaoNormalizada[m][j] = (distribuicaoPopulacao[m][j] / soma) * 100;
            }
        }
        return distribuicaoNormalizada;
    }

    public static String notacaoCientifica(Double num) {
        int expoente = 0;
        String notacaocientifica;
        while (num >= 10) {
            num = num / 10;
            num = num - (num % Math.pow(10, -expoente));
            expoente++;
        }
        notacaocientifica = String.format("%.2f", num) + "*10^" + expoente;
        return notacaocientifica;
    }

    public static Double[][] VetorProprio(Double[][] matrizvetores, int coluna) {
        Double[][] vetorProprio = new Double[matrizvetores.length][1];
        for (int i = 0; i < matrizvetores.length; i++) {
            vetorProprio[i][0] = matrizvetores[i][coluna];
        }
        return vetorProprio;
    }

    public static Double[][] ConversorDedoubleParaDouble(double[][] matriz) {
        Double[][] inverso = new Double[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++)
            for (int j = 0; j < matriz[0].length; j++)
                inverso[i][j] = matriz[i][j];
        return inverso;
    }

    public static Double ValorProprio(double[][] matrizleslie) {
        // Criar objeto do tipo Matriz
        Matrix a = new Basic2DMatrix(matrizleslie);
        //Obtem valores e vetores próprios fazendo "Eigen Decomposition"

        EigenDecompositor eigenD = new EigenDecompositor(a);
        Matrix[] mattD = eigenD.decompose();

        // converte objeto Matrix (duas matrizes)  para array Java

        //double matA [][]= mattD[0].toDenseMatrix().toArray();
        double[][] matB = mattD[1].toDenseMatrix().toArray();

        //autovalor
        double valorAtual = 0.0;
        double valorAnterior = 0.0;
        int controlo = 0;
        for (int n = 0; n < matB.length; n++) {
            for (int m = 0; m < matB.length; m++) {
                matB[n][m] = Double.parseDouble(String.format("%.4f", matB[n][m]).replace(",", "."));
                if (m == 0) {
                    valorAnterior = matB[n][m];
                } else {
                    if (matB[n][m] != 0 && valorAnterior != 0) {
                        controlo = 1;
                    } else if (matB[n][m] != 0 && valorAnterior == 0) {
                        valorAnterior = matB[n][m];
                    }
                }
            }
            if (controlo == 1) {
                controlo = 0;
            } else {
                if (valorAnterior > valorAtual) {
                    valorAtual = valorAnterior;
                }
            }
        }
        return valorAtual;
    }

    public static Double[][] Decompositor(double[][] matrizleslie) {
        // Criar objeto do tipo Matriz
        Matrix a = new Basic2DMatrix(matrizleslie);
        //Obtem valores e vetores próprios fazendo "Eigen Decomposition"

        EigenDecompositor eigenD = new EigenDecompositor(a);
        Matrix[] mattD = eigenD.decompose();

        double[][] matrizvaloresproprios = mattD[0].toDenseMatrix().toArray();

        Double[][] matrizvalorespropriosDouble = ConversorDedoubleParaDouble(matrizvaloresproprios);

        return distribuicaoNormalizada(matrizvalorespropriosDouble);
    }

    public static double[][] ConversorStringparadouble(String[][] matriz) {
        double[][] matrizConvertida = new double[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int f = 0; f < matriz[0].length; f++) {
                matrizConvertida[i][f] = Double.parseDouble(matriz[i][f]);
            }
        }
        return matrizConvertida;
    }

    public static int procurarValorProprioMaior(Double valorproprio, Double[][] matrizVetores) {
        for (int i = 0; i < matrizVetores.length; i++) {
            for (int j = 0; j < matrizVetores[0].length; j++) {
                if (matrizVetores[i][j].equals(valorproprio)) {
                    return j;
                }
            }
        }
        return 0;
    }

    //************************************************************************************************
    //**************************************     OUTPUT        ***************************************
    //************************************************************************************************

    public static void outputFicheiro(String[] parametros, Double[][] distribuicaoNorm, Double[][] distribuicaoPop, Double[][] populacaoTotal, Double[][] variacaoPop, Double valorproprio, Double[][] vetorproprio) throws IOException {
        /*
        Este método envia o outup para um ficheiro

        param: parametros, populacaototal, variacao
         */
        File ficheiro = new File(parametros[6]);
        OutputStreamWriter bw = new OutputStreamWriter(new FileOutputStream(ficheiro), StandardCharsets.UTF_8);
        String linha;
        String coluna;
        bw.write("\n" +
                "█▀▀ ▀█░█▀ █▀▀█ █░░ █░░█ █▀▀ █▀▀█ █▀▀█   █▀▀▄ █▀▀   █▀▀ █▀▀ █▀▀█ █▀▀ █▀▀ ░▀░ █▀▀ █▀▀ \n" +
                "█▀▀ ░█▄█░ █░░█ █░░ █░░█ █░░ █▄▄█ █░░█   █░░█ █▀▀   █▀▀ ▀▀█ █░░█ █▀▀ █░░ ▀█▀ █▀▀ ▀▀█ \n" +
                "▀▀▀ ░░▀░░ ▀▀▀▀ ▀▀▀ ░▀▀▀ ▀▀▀ ▀░░▀ ▀▀▀▀   ▀▀▀░ ▀▀▀   ▀▀▀ ▀▀▀ █▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀ \n" +
                "                                    by Os Rijos                                      \n" +
                "##################################################################################\n");
        for (int i = 2; i < 5; i++) {
            if (parametros[i].equals("TRUE") || parametros[i].equals("S")) {
                if (i == 2) {
                    bw.write("#                              Valor Próprio: " + valorproprio + "                              #\n" +
                            "###################################################################################\n");
                    bw.write("\n################################### Vetor Próprio #################################\n");
                    bw.write("\n");
                    linha = "Classe";
                    coluna = "Geração N";

                    escreverEmTabelaFicheiro(vetorproprio, bw, true, linha, coluna);
                } else if (i == 3) {
                    bw.write("\n################################### População Total ###############################\n");
                    bw.write("\n");
                    linha = "População";
                    coluna = "Geração";
                    escreverEmTabelaFicheiro(populacaoTotal, bw, false, linha, coluna);
                } else {
                    bw.write("\n################################ Variação Entre Gerações ###########################\n");
                    bw.write("\n");
                    linha = "Variação";
                    coluna = "Geração";
                    preenchedorDeNullsParaDoubles(variacaoPop);
                    escreverEmTabelaFicheiro(variacaoPop, bw, false, linha, coluna);
                }
            }
        }
        bw.write("\n############################# Distribuição da População ###########################\n");
        bw.write("\n");
        linha = "Classe";
        coluna = "Geração";
        escreverEmTabelaFicheiro(distribuicaoPop, bw, false, linha, coluna);
        bw.write("\n############## Distribuição Normalizada da População em percentagem ###############\n");
        bw.write("\n");
        linha = "Classe";
        coluna = "Geração";
        escreverEmTabelaFicheiro(distribuicaoNorm, bw, true, linha, coluna);
        bw.close();
        System.out.println("Foi criado com sucesso um ficheiro de texto com o nome '" + parametros[6] + "' na pasta do projeto!");
    }

    public static void escreverEmTabelaFicheiro(Double[][] matriz, OutputStreamWriter bw, boolean percentagem, String linha, String coluna) throws IOException {
        //As Classes vão as linhas e as Gerações vão ser as colunas
        //Começando pela coluna que identifica as classes
        //vamos definir um limite de Gerações que podem ser impressos na horizontal pq o bloco de notas tem limite de texto horizontal
        int limite = 41;
        final int limiteinicial = limite;
        int inicio = 0;
        String porcento;
        boolean fim = false;
        if (!percentagem) {
            porcento = "";
        } else {
            porcento = "%";
        }
        while (!fim) {
            if (matriz[0].length - limite < 0) {
                limite = matriz[0].length;
            }
            bw.write(String.format("%-15s", "[" + "Gerações" + "]"));
            for (int f = inicio; f < limite; f++) {
                if (!coluna.equals("Geração N")) {
                    bw.write(String.format("%-15s", "[" + coluna + " " + f + "]"));
                } else {
                    bw.write(String.format("%-15s", "[" + coluna + "]"));
                }
            }
            bw.write("\n");
            for (int i = 0; i < matriz.length; i++) {
                if (linha.equals("Classe")) {
                    bw.write(String.format("%-15s", linha + " " + (i + 1) + ":"));
                } else {
                    bw.write(String.format("%-15s", linha));
                }
                for (int k = inicio; k < limite; k++) {
                    if (matriz[i][k] > 999.00) {
                        bw.write(String.format("%-15s", notacaoCientifica(matriz[i][k]) + porcento));
                    } else {
                        bw.write(String.format("%-15s", String.format("%.2f", matriz[i][k]) + porcento));
                    }
                }
                bw.write("\n");
            }
            if (matriz[0].length - limite == 0) {
                fim = true;
            } else {
                bw.write("+++++++++++++++++++++++++++++++ CONTINUAÇÃO ++++++++++++++++++++++++++++++++++++\n");
            }
            inicio = limite;
            limite = limite + limiteinicial;
        }
    }

    public static void escreverEmTabela(Double[][] matriz, boolean percentagem, String linha, String coluna) {
        int limite = 12;
        final int limiteinicial = limite;
        int inicio = 0;
        String porcento;
        boolean fim = false;
        if (!percentagem) {
            porcento = "";
        } else {
            porcento = "%";
        }
        while (!fim) {
            if (matriz[0].length - limite < 0) {
                limite = matriz[0].length;
            }
            System.out.printf("%-15s", "[" + "Gerações" + "]");
            for (int f = inicio; f < limite; f++) {
                if (!coluna.equals("Geração N")) {
                    System.out.printf("%-15s", "[" + coluna + " " + f + "]");
                } else {
                    System.out.printf("%-15s", "[" + coluna + "]");
                }
            }
            System.out.print("\n");
            for (int i = 0; i < matriz.length; i++) {
                if (linha.equals("Classe")) {
                    System.out.printf("%-15s", linha + " " + (i + 1) + ":");
                } else {
                    System.out.printf("%-15s", linha);
                }
                for (int k = inicio; k < limite; k++) {
                    if (matriz[i][k] > 999.00) {
                        System.out.printf("%-15s", notacaoCientifica(matriz[i][k]) + porcento);
                    } else {
                        System.out.printf("%-15s", String.format("%.2f", matriz[i][k]) + porcento);
                    }
                }
                System.out.print("\n");
            }
            if (matriz[0].length - limite == 0) {
                fim = true;
            } else {
                System.out.print("+++++++++++++++++++++++++++++++ CONTINUAÇÃO ++++++++++++++++++++++++++++++++++++\n");
            }
            inicio = limite;
            limite = limite + limiteinicial;
        }
        System.out.println();
    }

    public static String guardargrafico() {

        //guardargrafico(Pergunta ao utilizador se este que guardar o gráfico que acabou de visualizar)
        System.out.println("Deseja guardar o gráfico acima visualizado?");
        System.out.println("Indique uma das seguintes opções:");
        System.out.println("S - Sim, desejo guardar! || N - Não, não desejo guardar!");
        System.out.print("\nResposta: ");

        ler.nextLine();
        String letra = ler.nextLine().toUpperCase(Locale.ROOT);

        //Se o utilizador escrever uma letra diferente de S e N, neste caso, o programa volta a pedir a letra

        while (!letra.equals("S") & !letra.equals("N")) {

            System.out.println("\n*********************** Letra inválida, digite de novo *************************\n");
            System.out.println("Deseja guardar o gráfico acima visualizado?");
            System.out.println("Indique uma das seguintes opções:");
            System.out.println("S - Sim, desejo guardar! || N - Não, não desejo guardar!");
            System.out.print("\nResposta: ");
            letra = ler.nextLine().toUpperCase(Locale.ROOT);
        }
        return letra;
    }

    //************************************************************************************************
    //**************************************     GRAFICOS      ***************************************
    //************************************************************************************************

    public static void graficoPopulacao(Double[][] dados, String titulo, String legenda, String[] parametros) throws IOException, InterruptedException {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.println("         -> Pressione BACKSPACE para continuar a execução do programa <-           ");
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

        criarScriptVisualizar(dados, titulo + " - "+nomePopulacao(parametros) , legenda);
        executarScript();
        if (guardargrafico().equals("S")) {
            g(parametros);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDateTime now = LocalDateTime.now();

            String nomeFicheiro = nomePopulacao(parametros) + titulo.replace(" ","") + "_" + dtf.format(now);
            criarScriptGuardar(Integer.parseInt(parametros[1]), dados, nomeFicheiro, titulo + " - "+nomePopulacao(parametros), legenda);
            if (Integer.parseInt(parametros[1]) == 2) {
                guardarModoTexto(nomeFicheiro);
            } else {

                executarScript();
            }
        }
    }

    public static void graficoPopulacaoNaoInterativo(int modo, Double[][] dados, String nomeFicheiro, String titulo, String legenda) throws IOException, InterruptedException {

        criarScriptGuardar(modo, dados, nomeFicheiro, titulo, legenda);
        if (modo == 2) {
            guardarModoTexto(nomeFicheiro);
        } else {
            executarScript();
        }
    }

    public static void criarScriptVisualizar(Double[][] dados, String titulo, String legenda) throws IOException {
        File ficheiro;
        ficheiro = new File("script.gp");
        FileWriter fw = new FileWriter(ficheiro);
        BufferedWriter bw = new BufferedWriter(fw);
        String grafico = "plot 'dadosGrafico.txt' u 1:2 w l title '" + legenda + " 1'";

        for (int n = 1; n <= dados.length; n++) {
            grafico = grafico + ", 'dadosGrafico.txt' u 1:" + (2 + n) + " w l title '" + legenda + " " + (n + 1) + "' ";
        }
        bw.write(
                //"set output \"grafico.png\"\n" +
                "set title \"" + titulo + "\"\n" +
                        "done = 0\n" +
                        "set xlabel \"X\"\n" +
                        "set ylabel \"Y\"\n" +
                        "set style data linespoints\n" +
                        "bind all 'BackSpace' 'done = 1'\n" +
                        "while(done == 0) { \n" +
                        "  " + grafico +
                        " \npause 1}");
        bw.close();
    }

    public static void criarScriptGuardar(int modo, Double[][] dados, String nomeFicheiro, String titulo, String legenda) throws IOException {
        File ficheiro;
        ficheiro = new File("script.gp");
        FileWriter fw = new FileWriter(ficheiro);
        BufferedWriter bw = new BufferedWriter(fw);
        String template = "#programa dos rijos\n";
        String grafico = "plot 'dadosGrafico.txt' u 1:2 w l title '" + legenda + " 1'";

        for (int n = 1; n < dados.length; n++) {
            grafico = grafico + ", 'dadosGrafico.txt' u 1:" + (2 + n) + " w l title '" + legenda + " " + (n + 1) + "' ";
        }

        if (modo == 1) {
            template = template + "set terminal png\n" + "set output \"" + nomeFicheiro + ".png\"\n";
        } else if (modo == 2) {
            template = template + "set terminal png\n" + "set terminal dumb\n";
        } else if (modo == 3) {
            template = template + "set terminal postscript eps\n" + "set output \"" + nomeFicheiro + ".eps\"\n";
        }
        template = template +
                "set title \"" + titulo + "\"\n" +
                "set xlabel \"X\"\n" +
                "set ylabel \"Y\"\n" +
                "set style data linespoints\n" +
                grafico;
        bw.write(template);
        bw.close();
    }

    public static void guardarModoTexto(String nomeFicheiro) throws InterruptedException, IOException {

        File ficheiro;
        if (!nomeFicheiro.contains(".txt")) {
            nomeFicheiro = nomeFicheiro + ".txt";
        }
        ficheiro = new File(nomeFicheiro);

        File gnuplot = new File("gnuplot\\bin\\gnuplot.exe");
        ProcessBuilder builder;
        if(gnuplot.isFile()){
            builder = new ProcessBuilder(
                    "gnuplot\\bin\\gnuplot.exe", "script.gp");
        }else{
            //System.out.println("Iniciando o programa instalado no pc");
            builder = new ProcessBuilder(
                    "C:\\Program Files\\gnuplot\\bin\\gnuplot.exe", "script.gp");
        }
        builder.redirectErrorStream(true);
        builder.redirectOutput(ficheiro);
        Process p = builder.start();
        p.waitFor();
    }

    public static void executarScript() throws IOException, InterruptedException {
        File gnuplot = new File("gnuplot\\bin\\gnuplot.exe");
        ProcessBuilder builder;
        if(gnuplot.isFile()){
            builder = new ProcessBuilder(
                    "gnuplot\\bin\\gnuplot.exe", "script.gp");
        }else{
            //System.out.println("Iniciando o programa instalado no pc");
            builder = new ProcessBuilder(
                    "C:\\Program Files\\gnuplot\\bin\\gnuplot.exe", "script.gp");
        }


        builder.redirectErrorStream(true);
        Process p = builder.start();
        p.waitFor();
    }

    public static void criarficheiroData(Double[][] matriz) throws IOException {
        File ficheiro;
        ficheiro = new File("dadosGrafico.txt");
        FileWriter fw = new FileWriter(ficheiro);
        BufferedWriter bw = new BufferedWriter(fw);
        int contador = 0;
        Double[][] transposta = new Double[matriz[0].length][matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                transposta[j][i] = matriz[i][j];
            }
        }
        for (int n = 0; n < transposta.length; n++) {
            bw.write(String.valueOf(contador));
            for (int m = 0; m < transposta[0].length; m++) {
                int valor = 6;
                int espacos = valor - String.valueOf(transposta[n][m]).length();
                int espacosContados = valor - String.valueOf(contador).length();
                String espacosString = "";
                String quinzeEspacos = "";

                for (int ns1 = 0; ns1 < espacosContados; ns1++) {
                    quinzeEspacos = quinzeEspacos + " ";
                }
                for (int ns = 0; ns < espacos; ns++) {
                    espacosString = espacosString + " ";
                }
                bw.write(quinzeEspacos + transposta[n][m] + espacosString);
            }
            contador++;
            bw.write("\n");
        }
        bw.write("\n");
        bw.close();
    }

    public static void printFicheiroData() throws FileNotFoundException {
        File ficheiro = new File("dadosGrafico.txt");
        Scanner scanner = new Scanner(ficheiro);
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.println("Ficheiro data: \n");
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }

    public static void graficosInterativo(Double[][] distribuicaoNorm, Double[][] distribuicaoPop, Double[][] populacaoTotal, Double[][] variacaoPop, String[] parametros) throws IOException, InterruptedException {
        //Opcoes dos gráficos para o modo Interativo

        int numero = menugraficos();
        while (numero != 5) {
            if (numero == 4) {
                criarficheiroData(distribuicaoNorm);
                printFicheiroData();
                graficoPopulacao(distribuicaoNorm, "Distribuicao Normalizada", "Classe", parametros);

            } else if (numero == 3) {
                criarficheiroData(distribuicaoPop);
                printFicheiroData();
                graficoPopulacao( distribuicaoPop, "Distribuicao Populacao", "Classe",parametros);

            } else if (numero == 2) {
                criarficheiroData(variacaoPop);
                printFicheiroData();
                graficoPopulacao(variacaoPop, "Variacao Populacao", "Variacao",parametros);

            } else if (numero == 1) {
                criarficheiroData(populacaoTotal);
                printFicheiroData();
                graficoPopulacao( populacaoTotal, "Populacao Total", "Populacao",parametros);
            }
            numero = menugraficos();
        }
        System.out.println();
    }

    public static void graficosNaoInterativo(Double[][] distribuicaoNorm, Double[][] distribuicaoPop, Double[][] populacaoTotal, Double[][] variacaoPop, String[] parametros) throws IOException, InterruptedException {
        //Opcoes dos gráficos para o modo não Interativo

        criarficheiroData(distribuicaoNorm);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        String nomeFicheiro = nomePopulacao(parametros)+"DistribuicaoNormalizada" + "_" + dtf.format(now);
        graficoPopulacaoNaoInterativo(Integer.parseInt(parametros[1]), distribuicaoNorm, nomeFicheiro, "Distribuicao Normalizada" +" - "+nomePopulacao(parametros), "Classe");

        criarficheiroData(distribuicaoPop);
        nomeFicheiro = nomePopulacao(parametros)+"DistribuicaoPopulacao" + "_" + dtf.format(now);
        graficoPopulacaoNaoInterativo(Integer.parseInt(parametros[1]), distribuicaoPop, nomeFicheiro, "Distribuicao Populacao"+" - "+nomePopulacao(parametros), "Classe");

        criarficheiroData(variacaoPop);
        nomeFicheiro = nomePopulacao(parametros)+"VariacaoPopulacao" + "_" + dtf.format(now);
        graficoPopulacaoNaoInterativo(Integer.parseInt(parametros[1]), variacaoPop, nomeFicheiro, "Variacao Populacao"+" - "+nomePopulacao(parametros), "Variacao");

        criarficheiroData(populacaoTotal);
        nomeFicheiro = nomePopulacao(parametros)+"PopulacaoTotal" + "_" + dtf.format(now);
        graficoPopulacaoNaoInterativo(Integer.parseInt(parametros[1]), populacaoTotal, nomeFicheiro, "Populacao Total"+" - "+nomePopulacao(parametros), "Populacao");
    }

    //************************************************************************************************
    //**************************************       MAIN        ***************************************
    //************************************************************************************************

    public static void printValores(String[] parametros, Double[][] distribuicaoPop, Double[][] distribuicaoNorm, Double[][] populacaoTotal, Double[][] variacaoPop, Double valorproprio, Double[][] vetorproprio) {
        //Este método dá print no main aos valores da distribuicaoPop, distribuicaoNorm, popTotal e variacaoPop

        String linha;
        String coluna;

        System.out.println("\n" +
                "█▀▀ ▀█░█▀ █▀▀█ █░░ █░░█ █▀▀ █▀▀█ █▀▀█   █▀▀▄ █▀▀   █▀▀ █▀▀ █▀▀█ █▀▀ █▀▀ ░▀░ █▀▀ █▀▀ \n" +
                "█▀▀ ░█▄█░ █░░█ █░░ █░░█ █░░ █▄▄█ █░░█   █░░█ █▀▀   █▀▀ ▀▀█ █░░█ █▀▀ █░░ ▀█▀ █▀▀ ▀▀█ \n" +
                "▀▀▀ ░░▀░░ ▀▀▀▀ ▀▀▀ ░▀▀▀ ▀▀▀ ▀░░▀ ▀▀▀▀   ▀▀▀░ ▀▀▀   ▀▀▀ ▀▀▀ █▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀ \n" +
                "                                  by Os Rijos                                        \n");

        if (parametros[2].equals("S") || parametros[3].equals("TRUE")) {
            System.out.println("******************************* Valor Próprio **********************************\n");
            System.out.println("                                    " + valorproprio);

            System.out.println("\n******************************* Vetor Próprio **********************************\n");
            linha = "Classe";
            coluna = "Geração N";
            escreverEmTabela(vetorproprio, true, linha, coluna);

        }
        if (parametros[3].equals("S") || parametros[3].equals("TRUE")) {
            System.out.println("**************************** População por Geração *****************************\n");
            linha = "População";
            coluna = "Geração";
            escreverEmTabela(populacaoTotal, false, linha, coluna);
        }
        if (parametros[4].equals("S") || parametros[4].equals("TRUE")) {
            System.out.println("****************************** Taxa de Variação ********************************\n");
            linha = "Variação";
            coluna = "Geração";
            escreverEmTabela(variacaoPop, false, linha, coluna);
        }

        System.out.println("************************** Distribuição da População ****************************\n");
        linha = "Classe";
        coluna = "Geração";
        escreverEmTabela(distribuicaoPop, false, linha, coluna);

        System.out.println("************ Distribuição Normalizada da População em percentagem ***************\n");
        linha = "Classe";
        coluna = "Geração";
        escreverEmTabela(distribuicaoNorm, true, linha, coluna);
    }

    public static void lerAgain(String[] parametros, String[][] matrizInput, int modo) throws IOException, InterruptedException {
        String[][] matrizleslie = MatrizLeslie(matrizInput);

        double[][] matrizLesliedouble = ConversorStringparadouble(matrizleslie);
        Double[][] matrizVetores = Decompositor(matrizLesliedouble);
        Double ValorProprio = ValorProprio(matrizLesliedouble);

        int colunaAtual = procurarValorProprioMaior(ValorProprio, matrizVetores);
        Double[][] vetorProprio = VetorProprio(matrizVetores, colunaAtual);
        Double[][] distribuicaoPop = distribuicaoPopulacao(matrizInput, parametros);
        Double[][] distribuicaoNorm = distribuicaoNormalizada(distribuicaoPop);
        Double[][] populacaoTotal = populacaoaCadaGeracao(distribuicaoPop);
        Double[][] variacaoPop = variacaoPop(populacaoTotal);

        if (modo == 1) {
            printValores(parametros, distribuicaoPop, distribuicaoNorm, populacaoTotal, variacaoPop, ValorProprio, vetorProprio);
        } else {
            graficosInterativo(distribuicaoNorm, distribuicaoPop, populacaoTotal, variacaoPop, parametros);
        }
    }

    public static void mainInterativo(String[] parametros, String[][] matrizInput) throws IOException, InterruptedException {
        int controlo = 1;

        lerAgain(parametros, matrizInput, 1);
        while (controlo == 1) {
            int opcao = menuMainInterativo();
            if (opcao == 1) {
                parametros[5] = menuNovoFicheiro();
                matrizInput = LerDoFicheiro(parametros[5]);
                preenchedorDeNulls(matrizInput);
                lerAgain(parametros, matrizInput, 1);

            } else if (opcao == 2) {
                lerAgain(parametros, matrizInput, 2);
            } else if (opcao == 3) {
                System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                matrizInput = interacao(parametros, 0);
                preenchedorDeNulls(matrizInput);
                lerAgain(parametros, matrizInput, 1);
            } else {
                controlo = 2;
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Locale.setDefault(new Locale("en", "US"));

        //verificação dos parametros para o caso de o programa executar no modo não interativo.
        //também verifica se os ficheiros de input introduzidos são validos
        //Se a verificação encontrar anomalias, o programa dá a mensagem de erro e sai.
        int verificacao = verificacaoNumeroParametros(args);
        if (verificacao <= 0) {
            mensagemErro(verificacao);
            System.exit(0);
        }

        String[] parametros = new String[7];
        String[][] matrizInput = modoExecucao(args, parametros);

        preenchedorDeNulls(matrizInput);

        if (args.length <= 2) {
            mainInterativo(parametros, matrizInput);
            System.out.println("\n********************************************************************************\n");
            System.out.println("                         Este foi o Programa d'Os Rijos!                            ");
            System.out.println("\n********************************************************************************");
        } else {
            String[][] matrizleslie = MatrizLeslie(matrizInput);
            double[][] matrizLesliedouble = ConversorStringparadouble(matrizleslie);
            Double[][] matrizVetores = Decompositor(matrizLesliedouble);
            Double ValorProprio = ValorProprio(matrizLesliedouble);
            int colunaAtual = procurarValorProprioMaior(ValorProprio, matrizVetores);

            Double[][] vetorProprio = VetorProprio(matrizVetores, colunaAtual);
            Double[][] distribuicaoPop = distribuicaoPopulacao(matrizInput, parametros);
            Double[][] distribuicaoNorm = distribuicaoNormalizada(distribuicaoPop);
            Double[][] populacaoTotal = populacaoaCadaGeracao(distribuicaoPop);
            Double[][] variacaoPop = variacaoPop(populacaoTotal);

            outputFicheiro(parametros, distribuicaoNorm, distribuicaoPop, populacaoTotal, variacaoPop, ValorProprio, vetorProprio);
            graficosNaoInterativo(distribuicaoNorm, distribuicaoPop, populacaoTotal, variacaoPop, parametros);
            ProcessBuilder pb = new ProcessBuilder("Notepad.exe", parametros[6]);
            try {
                pb.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        limpeza();
    }
}