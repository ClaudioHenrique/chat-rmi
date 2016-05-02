package com.facape.Client;

import com.facape.Config.Config;
import com.facape.Server.InterfaceServidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by claudiohenrique on 02/05/16.
 */
public class Client implements InterfaceClient, Serializable {

    private static final long serialVersionUID = 1L;
    public Client(){}

    public static void main(String args[]){
        //vars
        int idCliente;
        String mensagemEnviada;

        try{
            System.setSecurityManager( new RMISecurityManager());
            Client client = new Client();
            Naming.rebind(Config.CLIENT_NAME, client);

            Remote referenciaRemota = Naming.lookup(Config.SERVER_NAME);

            InterfaceServidor a = (InterfaceServidor) referenciaRemota;

            idCliente = a.estabelecerConexao();
            System.out.println("CONEXÃO ESTABELECIDA! ID: #"+ idCliente);

            BufferedReader teclado = new BufferedReader( new InputStreamReader(System.in));

            System.out.print("Informe seu nome: ");
            String nomeCliente = teclado.readLine();
            System.out.print("Informe sua mensagem: ");
            mensagemEnviada = teclado.readLine();

            while (mensagemEnviada.compareTo("end") != 0) {
                a.sendToAll(mensagemEnviada, idCliente, nomeCliente);
                System.out.print("Informe sua mensagem: ");
                mensagemEnviada = teclado.readLine();
            }

            System.out.println("Saiu ...");
            System.out.println(a.liberarConexao(idCliente));
            System.exit(1);
        }catch(Exception e){
            System.out.println("Erro " + e.getMessage());
        }
    }

    @Override
    public void enviarMensagem(String mensagem) throws RemoteException {
        System.out.print(mensagem);
    }
}
