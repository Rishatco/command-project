package sagidllin.samsung.project.model;

import sagidllin.samsung.project.view.RunTestDialogController;
import sagidllin.samsung.project.view.Showtestdialogcontroller;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MonoThreadClientHandler implements Runnable {

        private static Socket clientDialog;

        public MonoThreadClientHandler(Socket client) {
            MonoThreadClientHandler.clientDialog = client;

        }

        @Override
        public void run() {

            try {
                // инициируем каналы общения в сокете, для сервера

                // канал записи в сокет следует инициализировать сначала канал чтения для избежания блокировки выполнения программы на ожидании заголовка в сокете
                ObjectOutputStream out = new ObjectOutputStream(clientDialog.getOutputStream());

                // канал чтения из сокета
                ObjectInputStream in = new ObjectInputStream(clientDialog.getInputStream());
                System.out.println("DataInputStream created");

                System.out.println("DataOutputStream  created");
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // основная рабочая часть //
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                Test test=new Test();
                // начинаем диалог с подключенным клиентом в цикле, пока сокет не
                // закрыт клиентом
                ArrayList<Object> answer=new ArrayList<Object>() {
                };

                Showtestdialogcontroller.numbers.set(Showtestdialogcontroller.numbers.get()+1);

                while (!clientDialog.isClosed()) {
                    if(Test.flag_to_read){
                        answer=(ArrayList<Object>)in.readObject();
                        if(answer.size()>0) Test.flag_to_read=false;}

                    // серверная нить ждёт в канале чтения (inputstream) получения
                    // данных клиента после получения данных считывает их



                    // и выводит в консоль
                    // инициализация проверки условия продолжения работы с клиентом
                    // по этому сокету по кодовому слову - quit в любом регистре
                    if(answer.size()>0){
                        break;
                    }
                    // если условие окончания работы не верно - продолжаем работу -
                    // отправляем эхо обратно клиенту


                    if(Test.flag_to_write) {
                        out.writeObject(Test.sentTestToAndroid);
                        Test.flag_to_write = false;
                        out.flush();
                    }
                    if(Test.flag_to_start){
                        out.writeBoolean(true);
                        out.flush();
                        Test.flag_to_start=false;

                    }
                    // освобождаем буфер сетевых сообщений


                    // возвращаемся в началло для считывания нового сообщения
                }

                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // основная рабочая часть //
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                Test.answers_data.add(answer);
                // если условие выхода - верно выключаем соединения
                System.out.println("Client disconnected");
                System.out.println("Closing connections & channels.");

                // закрываем сначала каналы сокета !
                in.close();
                out.close();

                // потом закрываем сокет общения с клиентом в нити моносервера
                clientDialog.close();

                System.out.println("Closing connections & channels - DONE.");
            } catch (IOException e) {
                e.printStackTrace();
            }
             catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

