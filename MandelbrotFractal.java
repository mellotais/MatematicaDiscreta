package fractal;


import java.awt.Color; // cores e manipular valores de cores;
import java.awt.Dimension; // define dimensões largura e altura;
import java.awt.Graphics; // desenhar e manipular gráficos 2D.
import java.awt.image.BufferedImage; //  manipular imagens em memória.
import javax.swing.JFrame; // funcionalidade básica de uma janela 
import javax.swing.JPanel; //  painel que pode ser usado como um componente de contêiner 


public class MandelbrotFractal extends JPanel {

	//constantes usadas
    private static final long serialVersionUID = 1L;
    private static final int LARGURA = 800;
    private static final int ALTURA = 600;
    private static final double MIN_X = -2.5;  // o pixel deve perrtencer a uma certa regiao ddo 
    private static final double MAX_X = 1.5;   // plano complexo que tem um numero que pertence ao conjunto
    private static final double MIN_Y = -2.0;
    private static final double MAX_Y = 2.0;
    private static final int MAX_ITERACOES = 1000;

    /*
     * x0 e y0 é o C
     * x e y é o Z
     * 
     */
    private BufferedImage imagem; // com essa classe BufferedImage é possível 
                                 // manipular os pixels da imagem

    // construtor
    public MandelbrotFractal() { 
        setPreferredSize(new Dimension(LARGURA, ALTURA)); //dimensão preferida do painel atual
        imagem = new BufferedImage(LARGURA, ALTURA, BufferedImage.TYPE_INT_RGB); // cria uma instancia
        gerarFractal(); // chaama o metodo que inicia a geracao
    }

    // Gera o fractal
    private void gerarFractal() {
        for (int x = 0; x < LARGURA; x++) {  // percorre cada coluna da imagem
            for (int y = 0; y < ALTURA; y++) {  // percorre cada linha da imagem
                double x0 = mapear(x, 0, LARGURA, MIN_X, MAX_X);  // mapeia a posição x 
                double y0 = mapear(y, 0, ALTURA, MIN_Y, MAX_Y);  // mapeia a posição y 
                
                /* após mapear ele pega esses pontos e chama a função que troca a
               	*	cor do fractal e faz as iterações
                */ 

                int cor = calcularMandelbrot(x0, y0);  // calcula o ponto (x0, y0) ou seja para C e muda a cor
                imagem.setRGB(x, y, cor);  
                }
        }
    }


    // calcula a cor do ponto no fractal 
    private int calcularMandelbrot(double x0, double y0) {
        double x = 0;
        double y = 0;
        int iteracoes = 0;
        
        //// |z|<=2 portanto z*z<=2^2 =4
        while (x * x + y * y < 4 && iteracoes < MAX_ITERACOES) { 
        	
        	// proxX = z^2 + C.
            double proxX = x * x - y * y + x0;
            double proxY = 2 * x * y + y0;
            x = proxX;
            y = proxY;
            iteracoes++;
        }

        // se atingir o max(acabar) ele muda a cor para preto, o resto fica vermelho
        if (iteracoes == MAX_ITERACOES) {
            return Color.BLACK.getRGB();
        } else {
            float matiz = (float) iteracoes / MAX_ITERACOES;
            return Color.getHSBColor(matiz, 1, 1).getRGB();
        }
    }

     //Mapeia os valores das coordenadas do fractal para os valores correspondentes na tela.
     private double mapear(int valor, int inicio1, int fim1, double inicio2, double fim2) {
    	 //faz a regra de três e converte o valor de entrada inicio, para a faixa de saída
        return inicio2 + (fim2 - inicio2) * ((double) valor - inicio1) / (fim1 - inicio1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagem, 0, 0, null);
    }

    // principal
    public static void main(String[] args) {
        JFrame frame = new JFrame("Fractal de Mandelbrot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MandelbrotFractal());
        frame.pack();
        frame.setVisible(true);
    }
}
