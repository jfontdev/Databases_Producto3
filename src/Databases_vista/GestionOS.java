package Databases_vista;

import Databases_controlador.ArticulosControlador;
import Databases_controlador.ClientesControlador;
import Databases_modelo.Articulo;
import Databases_modelo.ClienteEstandard;
import Databases_modelo.ClientePremium;
import Databases_modelo.Datos;
import enums.ClienteTipo;

import java.util.Scanner;

public class GestionOS {
    private Datos datos;
    private ClientesControlador clientesControlador;
    private ArticulosControlador articulosControlador;
    Scanner teclado = new Scanner(System.in);

    public GestionOS(){
        this.datos = new Datos();
        this.clientesControlador = new ClientesControlador(this.datos);
        this.articulosControlador = new ArticulosControlador(this.datos);
    }

    public void inicio(){
        boolean salir = false;
        char opcion;
        do {
            System.out.println("1. Crear Cliente");
            System.out.println("2. Mostrar Clientes");
            System.out.println("3. Mostrar Clientes Premium");
            System.out.println("4. Mostrar Clientes Estandard");
            System.out.println("5. Mostrar artículo");
            System.out.println("6. Registrar artículo");
            System.out.println("0. Salir de la aplicacion");
            opcion = pedirOpcion();
            switch (opcion){
                case '1':
                    crearCliente();
                    break;
                case '2':
                    listarClientes(teclado,null);
                    break;
                case '3':
                    listarClientes(teclado,ClienteTipo.PREMIUM);
                    break;
                case '4':
                    listarClientes(teclado,ClienteTipo.ESTANDARD);
                case '5':
                    listarArticulos();
                    break;
                case '6':
                    createArticulos();
                    break;
                case '0':
                    salir = true;
            }
        } while (!salir);
    }

    char pedirOpcion(){
        String respuesta;
        System.out.println("Elige una opcion (1,2,3,4 o 0):");
        respuesta = teclado.nextLine();
        if (respuesta.isEmpty()){
            respuesta = " ";
        }
        return respuesta.charAt(0);
    }
    public void createArticulos() {
        System.out.println("Crear articulo: ");
        System.out.println("codigo articulo: ");
        String codigoArticulo = teclado.nextLine();
        System.out.println("Descripcion: ");
        String descripcion = teclado.nextLine();
        System.out.println("Precio de venta: ");
        Float precioVenta = teclado.nextFloat();
        System.out.println("Coste envio: ");
        Float gastosEnvio = teclado.nextFloat();
        System.out.println("Tiempo preparacion: ");
        Integer tiempoPreparacion = teclado.nextInt();
        if(!articulosControlador.articuloRepe(codigoArticulo)) {
            Articulo articulo = new Articulo(codigoArticulo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
            this.articulosControlador.createArticle(articulo);
        } else {
           System.out.println("Ya existe este artículo");
        }
    }


    public void crearCliente() {
        System.out.println("Crear Cliente: ");
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("Apellido: ");
        String apellido = teclado.nextLine();
        System.out.print("Domicilio: ");
        String domicilio = teclado.nextLine();
        System.out.print("NIF: ");
        String nif = teclado.nextLine();
        System.out.print("Email: ");
        String email = teclado.nextLine();
        if(clientesControlador.clientExists(email)){
            System.out.println("El cliente ya existe");
            return;
        }
        System.out.print("Tipo (1) Premium (2) Estandard: ");
        String tipo;
        do{
            tipo = teclado.nextLine();
        }while (!"12".contains(tipo));
        switch (tipo){
            case "1":
                ClientePremium clientePremium = new ClientePremium(nombre,apellido,domicilio,nif,email,ClienteTipo.PREMIUM);
                this.clientesControlador.createClient(clientePremium);
                break;
            case "2":
                ClienteEstandard clienteEstandard = new ClienteEstandard(nombre,apellido,domicilio,nif,email,ClienteTipo.ESTANDARD);
                this.clientesControlador.createClient(clienteEstandard);
                break;
        }
    }

    public void listarClientes(Scanner scanner, ClienteTipo clienteTipo) {
        if (clienteTipo != null) {
            if (clienteTipo == ClienteTipo.ESTANDARD) {
                clientesControlador.standardClientList();
            }
            if (clienteTipo == ClienteTipo.PREMIUM) {
                clientesControlador.premiumClientList();
            }
        } else {
            clientesControlador.clientList();
        }

    }
    public void listarArticulos() {
        this.articulosControlador.articleList();
    }
}
