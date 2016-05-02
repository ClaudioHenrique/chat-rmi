package com.facape.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by claudiohenrique on 02/05/16.
 */
public interface InterfaceClient extends Remote {
    public void enviarMensagem(String mensagem) throws RemoteException;
}
