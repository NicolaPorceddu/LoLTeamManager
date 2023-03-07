package com.example.bacci.lolteammanager;

import java.util.ArrayList;
import java.util.Arrays;

public class UserFactory {
    private static UserFactory instance;
    private static ArrayList<User> userList = new ArrayList<>();

    private UserFactory(){
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        User user5 = new User();
        User user6 = new User();
        User user7 = new User();
        User user8 = new User();
        User user9 = new User();
        User user10 = new User();
        User user11 = new User();
        User user12 = new User();
        User user13 = new User();
        User user14 = new User();
        User user15 = new User();

        /*Players squadra 1 "IUMmy Team"*/
        user1.setInGameName("SirNikoP");
        user1.setUsername("NicolaP96");
        user1.setPassword("ciaone");
        user1.setEmail("nicolap@gmail.com");
        user1.setSquadra(true);
        user1.setLivello(167);
        user1.setIdSquadra(1);
        user1.setEta(36);
        user1.setRank(Team.Rank.Silver);
        user1.setDivisione(1);
        user1.setRuoli(new ArrayList<String>(Arrays.asList("Support", "Toplaner")));
        user1.setKDA(new ArrayList<Double>(Arrays.asList(4.2, 9.9, 9.5)));
        user1.setFriendList(new ArrayList<User>(Arrays.asList(user2, user3, user4, user5, user6, user15)));

        user2.setInGameName("frensisssss");
        user2.setUsername("frensis");
        user2.setPassword("fucsia");
        user2.setEmail("francescab@gmail.com");
        user2.setSquadra(true);
        user2.setLivello(25);
        user2.setIdSquadra(1);
        user2.setEta(25);
        user2.setRank(Team.Rank.Unranked);
        user2.setDivisione(0);
        user2.setRuoli(new ArrayList<String>(Arrays.asList("ADC", "Midlaner")));
        user2.setKDA(new ArrayList<Double>(Arrays.asList(3.7, 8.9, 7.5)));
        user2.setFriendList(new ArrayList<User>(Arrays.asList(user1, user3, user4, user5)));

        user3.setInGameName("serenella");
        user3.setUsername("Serenas");
        user3.setPassword("giallo");
        user3.setEmail("serenas@gmail.com");
        user3.setSquadra(true);
        user3.setLivello(89);
        user3.setIdSquadra(1);
        user3.setEta(28);
        user3.setRank(Team.Rank.Silver);
        user3.setDivisione(2);
        user3.setRuoli(new ArrayList<String>(Arrays.asList("Jungler", "Toplaner")));
        user3.setKDA(new ArrayList<Double>(Arrays.asList(8.9, 6.7, 9.1)));
        user3.setFriendList(new ArrayList<User>(Arrays.asList(user1, user2, user4, user5)));

        user4.setInGameName("Coky");
        user4.setUsername("FedeCoky");
        user4.setPassword("bento");
        user4.setEmail("federicop@gmail.com");
        user4.setSquadra(true);
        user4.setLivello(121);
        user4.setIdSquadra(1);
        user4.setEta(24);
        user4.setRank(Team.Rank.Bronze);
        user4.setDivisione(4);
        user4.setRuoli(new ArrayList<String>(Arrays.asList("Toplaner", "ADC")));
        user4.setKDA(new ArrayList<Double>(Arrays.asList(4.5, 9.7, 8.8)));
        user4.setFriendList(new ArrayList<User>(Arrays.asList(user1, user2, user3, user5)));

        user5.setInGameName("m4bb0");
        user5.setUsername("AnBu");
        user5.setPassword("viabiasi");
        user5.setEmail("andreab@gmail.com");
        user5.setSquadra(true);
        user5.setLivello(115);
        user5.setIdSquadra(1);
        user5.setEta(24);
        user5.setRank(Team.Rank.Unranked);
        user5.setDivisione(0);
        user5.setRuoli(new ArrayList<String>(Arrays.asList("Midlaner", "Support")));
        user5.setKDA(new ArrayList<Double>(Arrays.asList(4.1, 8.9, 7.5)));
        user5.setFriendList(new ArrayList<User>(Arrays.asList(user1, user2, user3, user4, user10, user13)));

        getUserList().add(user1);
        getUserList().add(user2);
        getUserList().add(user3);
        getUserList().add(user4);
        getUserList().add(user5);

        /*Players squadra 2 "Reformed Team"*/
        user6.setInGameName("IlKuro");
        user6.setUsername("KuroAshi");
        user6.setPassword("Sanji");
        user6.setEmail("gabrielep@gmail.com");
        user6.setSquadra(true);
        user6.setLivello(163);
        user6.setIdSquadra(2);
        user6.setEta(23);
        user6.setRank(Team.Rank.Bronze);
        user6.setDivisione(2);
        user6.setRuoli(new ArrayList<String>(Arrays.asList("ADC", "Support")));
        user6.setKDA(new ArrayList<Double>(Arrays.asList(5.1, 9.3, 8.3)));
        user6.setFriendList(new ArrayList<User>(Arrays.asList(user1, user7, user8, user9, user11)));

        user7.setInGameName("Achacha3");
        user7.setUsername("Achacha");
        user7.setPassword("reformed");
        user7.setEmail("reformedachacha@gmail.com");
        user7.setSquadra(true);
        user7.setLivello(153);
        user7.setIdSquadra(2);
        user7.setEta(23);
        user7.setRank(Team.Rank.Silver);
        user7.setDivisione(2);
        user7.setRuoli(new ArrayList<String>(Arrays.asList("Jungler", "Toplaner")));
        user7.setKDA(new ArrayList<Double>(Arrays.asList(3.9, 7.6, 9.1)));
        user7.setFriendList(new ArrayList<User>(Arrays.asList(user6, user9, user8)));

        user8.setInGameName("xSannar");
        user8.setUsername("xSannar");
        user8.setPassword("spinner");
        user8.setEmail("gabrieles@gmail.com");
        user8.setSquadra(true);
        user8.setLivello(150);
        user8.setIdSquadra(2);
        user8.setEta(23);
        user8.setRank(Team.Rank.Unranked);
        user8.setDivisione(0);
        user8.setRuoli(new ArrayList<String>(Arrays.asList("Toplaner", "Midlaner")));
        user8.setKDA(new ArrayList<Double>(Arrays.asList(5.1, 7.6, 6.1)));
        user8.setFriendList(new ArrayList<User>(Arrays.asList(user6, user7, user9, user14)));

        user9.setInGameName("xSimb");
        user9.setUsername("Trincas");
        user9.setPassword("LeOui");
        user9.setEmail("xtrincas@gmail.com");
        user9.setSquadra(true);
        user9.setLivello(30);
        user9.setIdSquadra(2);
        user9.setEta(23);
        user9.setRank(Team.Rank.Unranked);
        user9.setDivisione(0);
        user9.setRuoli(new ArrayList<String>(Arrays.asList("Midlaner", "Jungler")));
        user9.setKDA(new ArrayList<Double>(Arrays.asList(4.2, 8.9, 9.7)));
        user9.setFriendList(new ArrayList<User>(Arrays.asList(user6, user7, user8, user12)));

        getUserList().add(user6);
        getUserList().add(user7);
        getUserList().add(user8);
        getUserList().add(user9);

        /*Players free agent*/
        user10.setInGameName("zanza");
        user10.setUsername("SamuZ");
        user10.setPassword("bestmid");
        user10.setEmail("samuz@gmail.com");
        user10.setSquadra(false);
        user10.setLivello(49);
        user10.setEta(18);
        user10.setRank(Team.Rank.Silver);
        user10.setDivisione(2);
        user10.setRuoli(new ArrayList<String>(Arrays.asList("Midlaner", "Support")));
        user10.setKDA(new ArrayList<Double>(Arrays.asList(4.2, 9.3, 9.2)));
        user10.setFriendList(new ArrayList<User>(Arrays.asList(user5, user11)));

        user11.setInGameName("Coloplast");
        user11.setUsername("Rob");
        user11.setPassword("bestadc");
        user11.setEmail("rob@gmail.com");
        user11.setSquadra(false);
        user11.setLivello(80);
        user11.setEta(22);
        user11.setRank(Team.Rank.Bronze);
        user11.setDivisione(2);
        user11.setRuoli(new ArrayList<String>(Arrays.asList("ADC", "Support")));
        user11.setKDA(new ArrayList<Double>(Arrays.asList(5.6, 7.9, 9.9)));
        user11.setFriendList(new ArrayList<User>(Arrays.asList(user6, user10, user13, user14)));

        user12.setInGameName("FeelNothing");
        user12.setUsername("Joe");
        user12.setPassword("nothing");
        user12.setEmail("joe@gmail.com");
        user12.setSquadra(false);
        user12.setLivello(30);
        user12.setEta(25);
        user12.setRank(Team.Rank.Bronze);
        user12.setDivisione(1);
        user12.setRuoli(new ArrayList<String>(Arrays.asList("Toplaner", "Jungler")));
        user12.setKDA(new ArrayList<Double>(Arrays.asList(6.1, 8.3, 7.5)));
        user12.setFriendList(new ArrayList<User>(Arrays.asList(user9)));

        user13.setInGameName("xMebo");
        user13.setUsername("Mebolas");
        user13.setPassword("meloniii");
        user13.setEmail("xmebo@gmail.com");
        user13.setSquadra(false);
        user13.setLivello(40);
        user13.setEta(22);
        user13.setRank(Team.Rank.Bronze);
        user13.setDivisione(2);
        user13.setRuoli(new ArrayList<String>(Arrays.asList("Support", "ADC")));
        user13.setKDA(new ArrayList<Double>(Arrays.asList(4.2, 7.1, 9.5)));
        user13.setFriendList(new ArrayList<User>(Arrays.asList(user5, user11, user14)));

        user14.setInGameName("M3ningoCollu");
        user14.setUsername("TheDoctor");
        user14.setPassword("nibba");
        user14.setEmail("collu@gmail.com");
        user14.setSquadra(false);
        user14.setLivello(96);
        user14.setEta(22);
        user14.setRank(Team.Rank.Unranked);
        user14.setDivisione(0);
        user14.setRuoli(new ArrayList<String>(Arrays.asList("Toplaner", "Midlaner")));
        user14.setKDA(new ArrayList<Double>(Arrays.asList(3.9, 6.4, 7.1)));
        user14.setFriendList(new ArrayList<User>(Arrays.asList(user8, user11, user13, user15)));

        user15.setInGameName("KhadaJhin6");
        user15.setUsername("MiniPorci");
        user15.setPassword("22xd");
        user15.setEmail("miniporci@gmail.com");
        user15.setSquadra(false);
        user15.setLivello(106);
        user15.setEta(16);
        user15.setRank(Team.Rank.Unranked);
        user15.setDivisione(0);
        user15.setRuoli(new ArrayList<String>(Arrays.asList("Support", "ADC")));
        user15.setKDA(new ArrayList<Double>(Arrays.asList(5.2, 10.9, 8.5)));
        user15.setFriendList(new ArrayList<User>(Arrays.asList(user1, user14)));

        getUserList().add(user10);
        getUserList().add(user11);
        getUserList().add(user12);
        getUserList().add(user13);
        getUserList().add(user14);
        getUserList().add(user15);
    }

    public static UserFactory getInstance(){
        if (instance == null)
            instance = new UserFactory();
        return instance;
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }

    public static void setUserList(ArrayList<User> userList) {
        UserFactory.userList = userList;
    }

    public boolean loginUsername(String username, String password){
        for(User user : getUserList())
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return true;

        return false;
    }

    public User getUtente(String username, String password){
        for(User user : getUserList())
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;

        return null;
    }

    public User getUtenteByID(int id){
        for(User u : userList)
            if(u.getIdPlayer() == id) return u;

        return null;
    }

    public User getUtenteByInGameName(String str){
        for(User u : getUserList())
            if(u.getInGameName().equals(str)) return u;

        return null;
    }

    public void deletePlayer (String str){
        for(User u : userList)
            if(u.getInGameName().equals(str)) {
                userList.remove(u);
            }
    }

    public static ArrayList<User> getPlayersByIdTeam(int id){
        ArrayList<User> list = new ArrayList<>();
        for (User user : getUserList()) {
            if(user.getIdSquadra()==id)
                list.add(user);
        }
        return list;
    }
}
