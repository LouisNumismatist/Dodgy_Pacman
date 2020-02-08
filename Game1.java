public class Game1 {

    static GameArena currentGame;


    public static void main(String[] args) {
        currentGame = new GameArena(1920/2, 1080/2);
        
        while (true) { currentGame.pause(); }
        
    }
}