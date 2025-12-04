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
    }
}