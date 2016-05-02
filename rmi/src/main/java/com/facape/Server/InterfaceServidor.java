package com.facape.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by claudiohenrique on 02/05/16.
 */

public interface InterfaceServidor extends Remote {
    public int estabelecerConexao() throws RemoteException;
    public String liberarConexao(int idCliente) throws RemoteException;
    public void sendToAll(String msg, int idCliente, String nomeCliente) throws RemoteException;
    public double calcularOperacao(int primeiroNumero, char operacao, int segundoNumero) throws RemoteException;
}
