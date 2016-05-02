package com.facape.Server;

import com.facape.Client.InterfaceClient;
import com.facape.Config.Config;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

/**
 * Created by claudiohenrique on 02/05/16.
 */

public class Server implements InterfaceServidor, Serializable {


    private static final long serialVersionuID = 1L;
    private int idCliente;
    private Object[] clientesConectados;

    //@super
    public Server(){};


    public static void main(String args[]){
        try{
            System.setSecurityManager(new RMISecurityManager());
            Server servidor = new Server();
            System.out.println("Registrando servidor");
            Naming.rebind(Config.SERVER_NAME, servidor);
            System.out.println("SERVIDOR EXECUTADO");
        }catch(Exception e){
            System.out.println("Erro " + e.getMessage());
        }
    }

    @Override
    public int estabelecerConexao() throws RemoteException {
        idCliente++;
        try{
            clientesConectados[idCliente] = (InterfaceClient) Naming.lookup("Cliente");
        }catch(Exception e){
            System.out.println("Erro " + e.getMessage());
        }

        return idCliente;
    }

    @Override
    public String liberarConexao(int idCliente) throws RemoteException {
        for(int i = 0; i < this.clientesConectados.length; i++){
            if(i == idCliente){
                this.clientesConectados[idCliente] = null;
            }
        }
        idCliente--;
        return "Conexão liberada";
    }

    @Override
    public void sendToAll(String msg, int idCliente, String nomeCliente) throws RemoteException {
        for(int i = 0; i < this.clientesConectados.length; i++){
            if((clientesConectados[i] != null) && (i != idCliente)){
                ((InterfaceClient) this.clientesConectados[i]).enviarMensagem(nomeCliente+": "+msg);
            }
        }
    }

    @Override
    public double calcularOperacao(int primeiroNumero, char operacao, int segundoNumero) throws RemoteException {
       double resultado = 0;
       switch (operacao){
           case '+': resultado = primeiroNumero + segundoNumero;
               break;
           case '-': resultado = primeiroNumero - segundoNumero;
               break;
           case '*': resultado = primeiroNumero * segundoNumero;
               break;
           case '/': resultado = primeiroNumero/segundoNumero;
               break;
       }
        return resultado;
    }
}
