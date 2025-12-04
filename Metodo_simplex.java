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

            //Termino independiente
            System.out.println("valor de b:");
            tabla[i][columnas-1] = lector.nextDouble();

            if(tipo ==1){
                tabla[i][variables+i-1]=1;
            }else if(tipo ==2){
                tabla[i][variables+i-1]=-1;
            }else if(tipo ==3){
                tabla[i][variables+i-1]=1;
        
            }
        }
        System.out.println("Resolviendo");
        resolverSimplex(tabla, esMaximizar, variables, restricciones);
        lector.close();

    }
    public static void resolverSimplex(double[][] tabla, boolean esMaximizar, int variables, int restricciones) {
        int filas = tabla.length;
        int columnas = tabla[0].length;
        int iteracion = 1;
        boolean terminado = false;

        System.out.println("Tabla inicial: ");
        mostrarTablaSimplex(tabla, variables, restricciones);

        while (!terminado && iteracion<=20){
            System.out.println("iteracion "+iteracion);

            //verificar si es optimo
            terminado=true;

            if (esMaximizar){
                for(int j=0;j<columnas-1;j++){
                    if (tabla[0][j]<0){
                        terminado = false;
                        break;
                    }
                }
            }else {
                for(int j=0;j<columnas-1;j++){
                    if(tabla[0][j]>0){
                        terminado = false;
                        break;
                    }
                }

            }
            if(terminado){
                System.out.println("Solucion optima encontrada");
                break;
            }
            int colPivote = 0;

            if(esMaximizar){
                for(int j=1;j<columnas-1;j++){
                    if(tabla[0][j]<tabla[0][colPivote]){
                        colPivote = j;
                    }
                }
                System.out.println("Columna pivote: variable"+obtenerNombreVariable(colPivote,variables));

            }else{
                for(int j=1;j<columnas-1;j++){
                    if(tabla[0][j]>tabla[0][colPivote]){
                        colPivote=j;
                    }
                }
                System.out.println("Columna pivote: variable "+ obtenerNombreVariable(colPivote,variables));

            }
            //Encontrar fila pivote
            int filaPivote = -1;
            double razonMinima= Double.MAX_VALUE;

            for(int i=1;i<filas;i++){
                if(tabla[i][colPivote] > 0){
                    double razon = tabla[i][columnas-1] / tabla[i][colPivote];
                     if(razon >= 0 && razon < razonMinima){
                        razonMinima = razon;
                        filaPivote = i;
                     } 

                }
            }
            if(filaPivote == -1){
                System.out.println("Problema no acotado - solución infinita");
                return;
            }
            System.out.println("Fila pivote: restricción " + filaPivote);
            System.out.println("Elemento pivote: " + tabla[filaPivote][colPivote]);

            //operacion pivote
            double pivote = tabla[filaPivote][colPivote];

            for(int j=0;j<columnas;j++){
                tabla[filaPivote][j] = tabla[filaPivote][j]/pivote;
            }
             for(int i = 0; i < filas; i++){
                if(i != filaPivote){
                    double factor = tabla[i][colPivote]; 
                    for(int j = 0; j < columnas; j++){
                       tabla[i][j] = tabla[i][j] - factor * tabla[filaPivote][j]; 
                    }
                }
             }
             System.out.println("Tabla despues de iteracion: ");
             mostrarTablaSimplex(tabla, variables, restricciones);
             iteracion++;
 
        }
        //Mostrar resultados
        System.out.println("Resultados");
        if(esMaximizar){
            System.out.println("Valor maximo de z="+tabla[0][columnas-1]);
        }else{
            System.out.println("Valor minimo de z= "+tabla[0][columnas-1]);
        }

        for(int j=0;j<variables;j++){
            double valor = 0;

            for(int i=1;i<filas;i++){
                if(Math.abs(tabla[i][j]-1.0)<0.0001){
                    boolean esUnitaria = true;
                    for(int k=0;k<columnas-1;k++){
                       if(k != j && Math.abs(tabla[i][k]) > 0.0001){
                        esUnitaria = false;
                        break;
                       }  
                    }
                    if(esUnitaria){
                        valor = tabla[i][columnas-1];
                        break;
                    }
                }
            }
           System.out.printf("x%d = %.4f\n", j+1, valor); 

        }
        for(int j=variables;j<columnas-1;j++){
            double valor = 0;

            for(int i = 1; i < filas; i++){
              if(Math.abs(tabla[i][j] - 1.0) < 0.0001){
                boolean esUnitaria = true;
                for(int k = 0; k < columnas-1; k++){
                   if(k != j && Math.abs(tabla[i][k]) > 0.0001){
                    esUnitaria = false;
                    break;
                   } 
                }
                if(esUnitaria){
                    valor=tabla[i][columnas-1];
                    break;
                }

              }  
            }
            if(valor>0.0001){
                System.out.printf("s%d = %.4f\n", j-variables+1, valor);
            }


        }
        

    }
        public static void mostrarTablaSimplex(double[][]tabla,int vars, int restr){
            int columnas=tabla[0].length;

          System.out.println(" ");
          for(int j=0;j<vars;j++){
            System.out.printf("    x%-2d", j+1);
         }
          for(int j = 0; j < restr; j++) {
            System.out.printf("    s%-2d", j+1);
         }
          System.out.print("    Sol");
        
         // Línea separadora
          System.out.print("\n   ");
          for(int j = 0; j < columnas+1; j++) {
            System.out.print("------");
         }
        
         // Filas
          System.out.print("\n Z |");
          for(int j = 0; j < columnas; j++) {
            System.out.printf(" %5.2f", tabla[0][j]);
         }
         System.out.println();
        
         for(int i = 1; i < tabla.length; i++) {
            System.out.printf("R%d |", i);
            for(int j = 0; j < columnas; j++) {
                System.out.printf(" %5.2f", tabla[i][j]);
            }
            System.out.println();
         }
        
         System.out.print("   ");
         for(int j = 0; j < columnas+1; j++) {
            System.out.print("------");
         }
          System.out.println();

         }
         public static String obtenerNombreVariable(int indice, int variables){
            if(indice<variables){
                return "x" + (indice+1);
            }else{
                return "s" + (indice-variables+1);
        }
    }

}

