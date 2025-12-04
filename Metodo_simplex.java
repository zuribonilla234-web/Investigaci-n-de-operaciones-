import java.util.Scanner;
public class Metodo_simplex{
    public static void main(String[]args){
        Scanner lector = new Scanner(System.in);

        int variables,restricciones;
        System.out.println("Metodo simplex");
        System.out.println("Elige una de las siguiente opciones: ");
        System.out.println("1. Maximizar");
        System.out.println("2. Minimizar");
        int opcion = lector.nextInt();

        boolean esMaximizar = (opcion==1);

        System.out.println("Ingresa cuantas variables desea tener: ");
        variables = lector.nextInt();
        System.out.println("Ingresa cuantas restricciones desea: ");
        restricciones=lector.nextInt();

        //tamaño de la tabla
        int filas = restricciones+1;
        int columnas = variables+1;
        double [][]tabla = new double[filas][columnas];

        //funcion objetivo
        System.out.println("Funcion objetivo");
        if(esMaximizar){
            System.out.println("Maximizar z= c1*x1 + c2*x2 + ...");
        }else{
            System.out.println("Minimizar z= c1*x1 + c2*x2 + ...");
        }

        System.out.println("Introduce los coeficientes: ");
        for(int j=0;j<variables;j++){
            System.out.println("Coeficiente de x "+(j+1)+": ");
            double coeficiente = lector.nextDouble();

            if(esMaximizar){
                tabla[0][j]=-coeficiente;
            }else{
                tabla[0][j]=coeficiente;
            }
        }
        for(int j=variables;j<columnas;j++){
            tabla[0][j]=0;
        }

        System.out.println("Restricciones");
        System.out.println("Formato: a1*x1 + a2*x2 + ....[<=, >=, =]");

        for(int i=1;i<=filas;i++){
            System.out.println("restriccion");
            //coeficientes de variables
            for(int j=0;j<variables;j++){
                System.out.println("Coeficiente de x "+(j+1)+": ");
                tabla[i][j] = lector.nextDouble();
            }
            //Tipo de restriccion
            System.out.println("Tipo de restriccion: ");
            System.out.println("1. <= ");
            System.out.println("2. >= ");
            System.out.println("3. = ");
            System.out.println("Elige del 1, 2 o 3: ");
            int tipo = lector.nextInt();
        }
    }
}