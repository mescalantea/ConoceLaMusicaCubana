package com.github.mescalantea.conocelamusicacubana.controller;

import android.content.Context;
import com.github.mescalantea.conocelamusicacubana.dao.QuestionDAO;
import com.github.mescalantea.conocelamusicacubana.dao.ScoreDAO;
import com.github.mescalantea.conocelamusicacubana.model.Connection;
import com.github.mescalantea.conocelamusicacubana.model.Question;
import com.github.mescalantea.conocelamusicacubana.model.Score;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {

    private String playerName;
    private final int MAX = 10;
    private int rightAnswerCount;
    private int lastQuestion;
    private ArrayList<Question> questions;
    private Context context;
    private static GameController INSTANCE = null;
    private QuestionDAO questionDAO;
    private ScoreDAO scoreDAO;
    private List<Integer> questionIds;
    private final int LAST_ID = 45;

    public int getLastQuestion() {
        return lastQuestion + 1;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getAverage(){
        return rightAnswerCount * 100 / MAX;
    }

    public Question nextQuestion(){
        Question q = null;
        if( lastQuestion < MAX - 1 ){
            // obtener una nueva pregunta
            q = questions.get(++lastQuestion) ;
        }
        return q;
    }

    public Question getCurrentQuestion(){
        return questions.get(lastQuestion);
    }

    public void plusRight(){
        rightAnswerCount++;
    }

    public void initGame(){
        rightAnswerCount = 0;
        lastQuestion = 0;
        Collections.shuffle(questionIds);
        questions = new ArrayList<>(MAX);

        for (int i = 0; i < 10; i++){
            questions.add(questionDAO.getQuestion(questionIds.get(i)));
        }

    }

    public void saveScore(){
        scoreDAO.createScore(new Score(getPlayerName(),getAverage()));
    }

    private GameController(Context c){

        context = c;
        questionDAO = new QuestionDAO(context);
        scoreDAO = new ScoreDAO(context);
        questionIds = new ArrayList<>(LAST_ID);

        for (int i = 1; i <= LAST_ID; i++){
            questionIds.add(i);
        }
    }

    private synchronized static void createInstance(Context c) {
        if (INSTANCE == null) {
            INSTANCE = new GameController(c);
        }
    }

    public static GameController getInstance(Context c) {
        if (INSTANCE == null) createInstance(c);
        return INSTANCE;
    }


}
