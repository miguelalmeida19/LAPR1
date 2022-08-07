import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class mainTest {

    @org.junit.jupiter.api.Test
    public void MatrizLeslie() {

        //TESTE 1

        String[][] input = {{"2.0", "5.0", "10.0", "1.0"},
                {"0.2", "0.3", "0.6", "0"},
                {"0.1", "0.2", "3.0", "0"}};

        String[][] a = {{"0.1", "0.2", "3.0", "0"},
                {"0.2", "0", "0", "0"},
                {"0", "0.3", "0", "0"},
                {"0", "0", "0.6", "0"}};

        Assertions.assertArrayEquals(a, main.MatrizLeslie(input));

        //TESTE 2

        String[][] input2 = {{"20.0", "10.0", "40.0", "30.0"},
                {"0.5", "0.8", "0.5", "0"},
                {"0.5", "2.4", "1.0", "0"}};

        String[][] b = {{"0.5", "2.4", "1.0", "0"},
                {"0.5", "0", "0", "0"},
                {"0", "0.8", "0", "0"},
                {"0", "0", "0.5", "0"}};

        Assertions.assertArrayEquals(b, main.MatrizLeslie(input2));
    }

    @org.junit.jupiter.api.Test
    void multiplicacaoDeMatrizesQuadradas() {
        Double[][] a = {{0.5, 2.4, 1.0, 0.0},
                {0.5, 0.0, 0.0, 0.0},
                {0.0, 0.8, 0.0, 0.0},
                {0.0, 0.0, 0.5, 0.0}};

        Double[][] input = {{1.45, 2.0, 0.5, 0.0},
                {0.25, 1.2, 0.5, 0.0},
                {0.4, 0.0, 0.0, 0.0},
                {0.0, 0.4, 0.0, 0.0}};

        Assertions.assertArrayEquals(input, main.multiplicacaoDeMatrizesQuadradas(a, a));
    }

    @org.junit.jupiter.api.Test
    void multiplicacaoDeMatrizesComExpoente() {

        //TESTE 1

        Double[][] a = {{0.5, 2.4, 1.0, 0.0},
                {0.5, 0.0, 0.0, 0.0},
                {0.0, 0.8, 0.0, 0.0},
                {0.0, 0.0, 0.5, 0.0}};

        int expoente = 2;

        String[][] Ni = {{"20.0"}, {"10.0"}, {"40.0"}, {"30.0"}};

        Double[][] resultado = {{1.45, 2.0, 0.5, 0.0},
                {0.25, 1.2, 0.5, 0.0},
                {0.4, 0.0, 0.0, 0.0},
                {0.0, 0.4, 0.0, 0.0}};

        Assertions.assertArrayEquals(resultado, main.multiplicacaoDeMatrizesComExpoente(a, a, expoente, Ni));

        //TESTE 2

        Double[][] b = {{0.10, 0.20, 3.0, 0.0},
                {0.2, 0.0, 0.0, 0.0},
                {0.0, 0.3, 0.0, 0.0},
                {0.0, 0.0, 0.6, 0.0}};

        int expoente2 = 3;

        String[][] Ni2 = {{"10.0"}, {"20.0"}, {"30.0"}, {"40.0"}};

        Double[][] resultado2 = {{0.189, 0.1, 0.15000000000000002, 0.0},
                {0.010000000000000002, 0.18400000000000002, 0.06000000000000001, 0.0},
                {0.006, 0.012, 0.18, 0.0},
                {0.036, 0.0, 0.0, 0.0}};

        Assertions.assertArrayEquals(resultado2, main.multiplicacaoDeMatrizesComExpoente(b, b, expoente2, Ni2));
    }

    @org.junit.jupiter.api.Test
    void multiplicacaoporN() {

        //TESTE 1

        Double[][] a = {{0.5, 2.4, 1.0, 0.0},
                {0.5, 0.0, 0.0, 0.0},
                {0.0, 0.8, 0.0, 0.0},
                {0.0, 0.0, 0.5, 0.0}};

        String[][] input = {{"20.0"}, {"10.0"}, {"40.0"}, {"30.0"}};

        Double[][] b = {{74.0}, {10.0}, {8.0}, {20.0}};

        Assertions.assertArrayEquals(b, main.multiplicacaoporN(a, input));

        //TESTE 2

        Double[][] c = {{0.10, 0.20, 3.0, 0.0},
                {0.2, 0.0, 0.0, 0.0},
                {0.0, 0.3, 0.0, 0.0},
                {0.0, 0.0, 0.6, 0.0}};

        String[][] input2 = {{"10.0"}, {"20.0"}, {"30.0"}, {"40.0"}};

        Double[][] d = {{95.0}, {2.0}, {6.0}, {18.0}};

        Assertions.assertArrayEquals(d, main.multiplicacaoporN(c, input2));
    }

    @org.junit.jupiter.api.Test
    void soma() {

        //TESTE 1

        Double[][] a = {{20.0}, {40.0}, {20.0}, {20.0}};
        double soma = 100.0;

        Assertions.assertEquals(soma, main.soma(a));

        //TESTE 2

        Double[][] b = {{10.0}, {20.0}, {30.0}, {40.0}};
        double soma1 = 100.0;

        Assertions.assertEquals(soma1, main.soma(b));
    }

    @org.junit.jupiter.api.Test
    void variacaoPop() {

        //TESTE 1

        Double[][] PopulacaoTotal = {{100.00, 112.00, 118.00, 199.40}};
        Double[][] a = {{1.12, 1.0535714285714286, 1.6898305084745764}};

        Assertions.assertArrayEquals(a, main.variacaoPop(PopulacaoTotal));

        //TESTE 2

        Double[][] PopulacaoTotal1 = {{100.00, 121.00, 51.10, 20.03}};
        Double[][] b = {{1.21, 0.4223140495867769, 0.39197651663405086}};

        Assertions.assertArrayEquals(b, main.variacaoPop(PopulacaoTotal1));
    }

    @org.junit.jupiter.api.Test
    void populacaoaCadaGeracao() {

        //TESTE 1

        Double[][] a = {{20.0, 74.0, 69.0}, {10.0, 10.0, 37.0}, {40.0, 8.0, 8.0}, {30.0, 20.0, 4.0}};
        Double[][] resultado = {{100.0, 112.0, 118.0}};

        Assertions.assertArrayEquals(resultado, main.populacaoaCadaGeracao(a));

        //TESTE 2

        Double[][] b = {{10.00, 95.00, 27.90, 8.39}, {20.00, 2.00, 19.00, 5.58}, {30.00, 6.00, 0.60, 5.70}, {40.00, 18.00, 3.60, 0.36}};
        Double[][] resultado1 = {{100.0, 121.0, 51.1, 20.03}};

        Assertions.assertArrayEquals(resultado1, main.populacaoaCadaGeracao(b));
    }

    @org.junit.jupiter.api.Test
    void distribuicaoPopulacao() {

        //TESTE 1

        String[][] input = {{"20", "10", "40", "30"}, {"0.50", "0.80", "0.50", "0"}, {"0.50", "2.40", "1.00", "0.00"}};
        String[] parametros = {"2"};
        Double[][] resultado = {{20.0, 74.0, 69.0}, {10.0, 10.0, 37.0}, {40.0, 8.0, 8.0}, {30.0, 20.0, 4.0}};

        Assertions.assertArrayEquals(resultado, main.distribuicaoPopulacao(input, parametros));

        //TESTE 2

        String[][] input1 = {{"10", "20", "30", "40"}, {"0.2", "0.3", "0.6", "0"}, {"0.1", "0.2", "3.0", "0"}};
        String[] parametros1 = {"4"};
        Double[][] resultado1 = {{10.00, 95.00, 27.90, 8.39, 19.055}, {20.00, 2.00, 19.000000000000004, 5.580000000000001, 1.6780000000000004}, {30.00, 6.00, 0.60, 5.699999999999999, 1.6740000000000002}, {40.00, 18.00, 3.5999999999999996, 0.36, 3.4199999999999995}};
        Assertions.assertArrayEquals(resultado1, main.distribuicaoPopulacao(input1, parametros1));
    }

    @org.junit.jupiter.api.Test
    void distribuicaoNormalizada() {

        //TESTE 1

        Double[][] input = {{20.0, 74.0, 69.0}, {10.0, 10.0, 37.0}, {40.0, 8.0, 8.0}, {30.0, 20.0, 4.0}};
        Double[][] resultado = {{20.0, 66.07142857142857, 58.47457627118644}, {10.0, 8.928571428571429, 31.35593220338983}, {40.0, 7.142857142857142, 6.779661016949152}, {30.0, 17.857142857142858, 3.389830508474576}};

        Assertions.assertArrayEquals(resultado, main.distribuicaoNormalizada(input));

        //TESTE 2

        Double[][] input1 = {{10.00, 95.00, 27.90, 8.39, 19.055}, {20.00, 2.00, 19.000000000000004, 5.580000000000001, 1.6780000000000004}, {30.00, 6.00, 0.60, 5.699999999999999, 1.6740000000000002}, {40.00, 18.00, 3.5999999999999996, 0.36, 3.4199999999999995}};
        Double[][] resultado1 = {{10.0, 78.51239669421489, 54.598825831702534, 41.88716924613081, 73.77937817013203}, {20.0, 1.6528925619834711, 37.18199608610568, 27.858212680978532, 6.497076702675497}, {30.0, 4.958677685950414, 1.1741682974559684, 28.45731402895656, 6.481589034731096}, {40.0, 14.87603305785124, 7.04500978473581, 1.7973040439340986, 13.241956092461377}};

        Assertions.assertArrayEquals(resultado1, main.distribuicaoNormalizada(input1));
    }

    @Test
    void survival() {

        //TESTE 1

        String[][] input = {{"20", "30"}, {"0.2", "0"}, {"0.3", "0.4"}};
        String[] resultado = {"0.2"};

        Assertions.assertArrayEquals(resultado, main.Survival(input));

        //TESTE 2

        String[][] input1 = {{"1", "3", "5", "7", "9"}, {"0.2", "0.5", "0.9", "0.2", "0"}, {"0.0", "0.3", "0.4", "0.1", "0.6"}};
        String[] resultado1 = {"0.2", "0.5", "0.9", "0.2"};

        Assertions.assertArrayEquals(resultado1, main.Survival(input1));
    }

    @Test
    void fecundidade() {
        //TESTE 1

        String[][] input = {{"20", "30"}, {"0.2", "0"}, {"0.3", "0.4"}};
        String[] resultado = {"0.3", "0.4"};

        Assertions.assertArrayEquals(resultado, main.Fecundidade(input));

        //TESTE 2

        String[][] input1 = {{"1", "3", "5", "7", "9"}, {"0.2", "0.5", "0.9", "0.2", "0"}, {"0.0", "0.3", "0.4", "0.1", "0.6"}};
        String[] resultado1 = {"0.0", "0.3", "0.4", "0.1", "0.6"};

        Assertions.assertArrayEquals(resultado1, main.Fecundidade(input1));
    }

    @Test
    void vetorProprio() {

        //TESTE 1

        Double[][] matrizvetores = {{70.15621187164243, -570.1562118716425}, {29.843788128357566, 670.1562118716425}};
        int coluna = 0;
        Double[][] resultado = {{70.15621187164243}, {29.843788128357566}};

        Assertions.assertArrayEquals(resultado, main.VetorProprio(matrizvetores, coluna));

        //TESTE 2

        Double[][] matrizvetores1 = {{71.43496211047061, -356.3760069973612, -116.83580468375683, -140.75734743987186, -19.777411501457724}, {17.334881870955694, 306.14162946904435, -2.323095599429765, -17.828887880817547, -49.98255305863145}, {6.309896175529922, -252.09547096214857, 88.63494007297868, 102.03290172654856, -33.32932008807435}, {3.0624025432155637, 116.45913604075697, -193.74921217614508, 166.96540520756605, 40.56013419618845}, {1.8578572998282208, 285.87071244970844, 324.273172386353, -10.412071613425212, 162.52915045197506}};
        int coluna1 = 0;
        Double[][] resultado1 = {{71.43496211047061}, {17.334881870955694}, {6.309896175529922}, {3.0624025432155637}, {1.8578572998282208}};

        Assertions.assertArrayEquals(resultado1, main.VetorProprio(matrizvetores1, coluna1));
    }

    @Test
    void conversorDedoubleParaDouble() {

        //TESTE 1

        double[][] input = {{0.9202015039731397, -0.6788676663287205}, {0.391445005186644, 0.7979346259081335}};
        Double[][] resultado = {{0.9202015039731397, -0.6788676663287205}, {0.391445005186644, 0.7979346259081335}};

        Assertions.assertArrayEquals(resultado, main.ConversorDedoubleParaDouble(input));

        //TESTE 2

        double[][] input1 = {{0.9558023863574243, 0.500862756311601, -0.5023096031541258, 0.3711979925385922, 0.15628265574717226}, {0.2632765932616503, -0.19722743414359517, -0.45230254347668486, -0.22875500214435596, -0.3315767546711472}, {0.10877964767950515, -0.5145320000947844, 0.01829851676209332, 0.021469276050923303, 0.6039748401277619}, {0.05992689590660445, -0.23893673940711896, 0.6752550527151806, 0.6141297547530863, -1.0413304874199099}, {0.04126728815556753, 0.9399644604767653, 0.8173603522583663, -2.695719359601216, 1.3678419819350789}};
        Double[][] resultado1 = {{0.9558023863574243, 0.500862756311601, -0.5023096031541258, 0.3711979925385922, 0.15628265574717226}, {0.2632765932616503, -0.19722743414359517, -0.45230254347668486, -0.22875500214435596, -0.3315767546711472}, {0.10877964767950515, -0.5145320000947844, 0.01829851676209332, 0.021469276050923303, 0.6039748401277619}, {0.05992689590660445, -0.23893673940711896, 0.6752550527151806, 0.6141297547530863, -1.0413304874199099}, {0.04126728815556753, 0.9399644604767653, 0.8173603522583663, -2.695719359601216, 1.3678419819350789}};

        Assertions.assertArrayEquals(resultado1, main.ConversorDedoubleParaDouble(input1));
    }

    @Test
    void valorProprio() {

        //TESTE 1

        double[][] input = {{0.3, 0.4}, {0.2, 0.0}};
        double resultado = 0.4702;

        Assertions.assertArrayEquals(new Double[]{resultado}, new Double[]{main.ValorProprio(input)});

        //TESTE 2

        double[][] input1 = {{0.6, 0.9, 0.4, 0.23, 0.5}, {0.2, 0.0, 0.0, 0.0, 0.0}, {0.0, 0.3, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.4, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.5, 0.0}};
        double resultado1 = 0.861;

        Assertions.assertArrayEquals(new Double[]{resultado1}, new Double[]{main.ValorProprio(input1)});
    }
}