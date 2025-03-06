package com.example.project;
import java.util.ArrayList;

import javax.swing.text.Utilities;


public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c){
        hand.add(c);
    }

    public String playHand(ArrayList<Card> communityCards){      
        if (allCards.size() < 5) {
            for (int i = 0; i < 3; i++) {
                allCards.add(communityCards.get(i));
            }
            for (int i = 0; i < 2; i++) {
                allCards.add(hand.get(i));
            }
        }
        sortAllCards();
        if (isRoyalFlush()) {
            return "Royal Flush";
        } else if (isStraightFlush()) {
            return "Straight Flush";
        } else if (isFourOfAKind()) {
            return "Four of a Kind";
        } else if (isFullHouse()) {
            return "Full House";
        } else if (isFlush()) {
            return "Flush";
        } else if (isStraight()) {
            return "Straight";
        } else if (isThreeOfAKind()) {
            return "Three of a Kind";
        } else if (isTwoPair()) {
            return "Two Pair";
        } else if (isOnePair()) {
            return "A Pair";
        } else if (isHighCard()) {
            return "High Card";
        }
        return "Nothing";
    }

    public void sortAllCards(){
        // sort allCards array list
        for (int i = 1; i < allCards.size(); i++) {
            int j = i;
            while (j > 0 && Utility.getRankValue(allCards.get(j - 1).getRank()) > Utility.getRankValue(allCards.get(j).getRank())) {
                allCards.set(j, allCards.set(j - 1, allCards.get(j)));
                j--;
            }
        }
        // sort hand array list
        for (int i = 1; i < hand.size(); i++) {
            int j = i;
            while (j > 0 && Utility.getRankValue(hand.get(j - 1).getRank()) > Utility.getRankValue(hand.get(j).getRank())) {
                hand.set(j, hand.set(j - 1, hand.get(j)));
                j--;
            }
        }
    } 

    public ArrayList<Integer> findRankingFrequency(){
        ArrayList<Integer> rankingFrequency = new ArrayList<>();
        for (int i = 0; i < ranks.length; i++) {
            rankingFrequency.add(0);
        }
        for (int i = 0; i < allCards.size(); i++) {
            for (int j = 0; j < ranks.length; j++) {
                if (allCards.get(i).getRank().equals(ranks[j])) {
                    rankingFrequency.set(j, rankingFrequency.get(j) + 1);
                }
            }
        }
        return rankingFrequency; 
    }

    public ArrayList<Integer> findSuitFrequency(){
        ArrayList<Integer> suitFrequency = new ArrayList<>();
        for (int i = 0; i < suits.length; i++) {
            suitFrequency.add(0);
        }
        for (int i = 0; i < allCards.size(); i++) {
            for (int j = 0; j < suits.length; j++) {
                if (allCards.get(i).getSuit().equals(suits[j])) {
                    suitFrequency.set(j, suitFrequency.get(j) + 1);
                }
            }
        }
        return suitFrequency; 
    }

   
    @Override
    public String toString(){
        return hand.toString();
    }

    public boolean isRoyalFlush() {
        boolean isRF = false;
        ArrayList<Integer> rankingFrequency = findRankingFrequency();
        ArrayList<Integer> suitsFrequency = findSuitFrequency();
        if (rankingFrequency.get(8) == 1 && rankingFrequency.get(9) == 1 && rankingFrequency.get(10) == 1 && rankingFrequency.get(11) == 1 && rankingFrequency.get(12) == 1) {
            if (suitsFrequency.get(0) == 5 || suitsFrequency.get(1) == 5 || suitsFrequency.get(2) == 5 || suitsFrequency.get(3) == 5) {
                isRF = true;
            }
        }
        return isRF;
    }

    public boolean isStraightFlush() {
        if (isStraight() && isFlush()) {
            return true;
        }
        return false;
    }

    public boolean isFourOfAKind() {
        int one = Utility.getRankValue(allCards.get(0).getRank());
        int two = Utility.getRankValue(allCards.get(1).getRank());
        int three = Utility.getRankValue(allCards.get(2).getRank());
        int four = Utility.getRankValue(allCards.get(3).getRank());
        int five = Utility.getRankValue(allCards.get(4).getRank());
        if (one == two && two == three && three == four) {
            return true;
        }
        if (two == three && three == four && four == five) {
            return true;
        }
        return false;
    }

    public boolean isFullHouse() {
        int one = Utility.getRankValue(allCards.get(0).getRank());
        int two = Utility.getRankValue(allCards.get(1).getRank());
        int three = Utility.getRankValue(allCards.get(2).getRank());
        int four = Utility.getRankValue(allCards.get(3).getRank());
        int five = Utility.getRankValue(allCards.get(4).getRank());
        if (one == two && two == three) {
            if (four == five) {
                return true;
            }
        }
        if (one == two) {
            if (three == four && four == five) {
                return true;
            }
        }
        return false;
    }

    public boolean isFlush() {
        for (int i = 0; i < allCards.size() - 1; i++) {
            if (!allCards.get(i).getSuit().equals(allCards.get(i + 1).getSuit())) {
                return false;
            }
        }
        return true;
    }

    public boolean isStraight() {
        boolean isStraight = false;
        if (Utility.getRankValue(allCards.get(0).getRank()) == Utility.getRankValue(allCards.get(1).getRank()) - 1) {
            if (Utility.getRankValue(allCards.get(1).getRank()) == Utility.getRankValue(allCards.get(2).getRank()) - 1) {
                if (Utility.getRankValue(allCards.get(2).getRank()) == Utility.getRankValue(allCards.get(3).getRank()) - 1) {
                    if (Utility.getRankValue(allCards.get(3).getRank()) == Utility.getRankValue(allCards.get(4).getRank()) - 1) {
                        isStraight = true;
                    }
                }
            }
        }
        return isStraight;
    }

    public boolean isThreeOfAKind() {
        ArrayList<Integer> rankingFrequency = findRankingFrequency();
        for (int i = 0; i < rankingFrequency.size(); i++) {
            if (rankingFrequency.get(i) == 3) {
                return true;
            }
        }
        return false;
    }

    public boolean isTwoPair() {
        ArrayList<Integer> rankingFrequency = findRankingFrequency();
        int counter = 0;
        for (int i = 0; i < rankingFrequency.size(); i++) {
            if (rankingFrequency.get(i) == 2) {
                counter++;
            }
            if (counter == 2) {
                return true;
            }
        }
        return false;
    }

    public boolean isOnePair() {
        ArrayList<Integer> rankingFrequency = findRankingFrequency();
        for (int i = 0; i < rankingFrequency.size(); i++) {
            if (rankingFrequency.get(i) == 2) {
                return true;
            }
        }
        return false;
    }

    public boolean isHighCard() {
        if (Utility.getRankValue(hand.get(1).getRank()) >= Utility.getRankValue(allCards.get(4).getRank())) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Player player = new Player();
        player.addCard(new Card("10", "♠"));
        player.addCard(new Card("J", "♠"));
        System.out.println(player.toString());
        
        // Community Cards
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("Q", "♠"));
        communityCards.add(new Card("K", "♠"));
        communityCards.add(new Card("A", "♠"));
        
        player.playHand(communityCards);
        String handResult = player.playHand(communityCards);
        System.out.println(handResult);
        System.out.println(player.getAllCards().toString());
    }
}
