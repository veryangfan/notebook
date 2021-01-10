package a.b.c;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by Fan at 16:17, 2021/1/10 .
 */
public class AddClient {
    AddServiceGrpc.AddServiceBlockingStub stub;
    ManagedChannel channel;

    public static void main(String[] args) {
        int a = 101;
        int b = 102;
        AddClient client = new AddClient();

        AddReply reply =
                client.stub.add(AddRequest.newBuilder().setA(a).setB(b).build());
        System.out.println(reply.getRes());
    }

    public AddClient(){
        channel = ManagedChannelBuilder
                .forAddress("127.0.0.1",9999)
                .usePlaintext()
                .build();
        stub =
                AddServiceGrpc.newBlockingStub(channel);
    }
}