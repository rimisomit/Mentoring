package com.mentoring.workshop.client;

public interface ClientService {
    void receiveOrder(Client client, boolean toRepair);

    void releaseOrder(Client client);
}
