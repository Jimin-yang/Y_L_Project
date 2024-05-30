package com.example.planvoice.network;

public class ExerciseResponse {

    private int ID;
    private String ExerciseName;
    private String BodyPart;
    private String ExerciseDescription;
    private String imageURL;

    public int getID() {
        return ID;
    }

    public String getExerciseName() {
        return ExerciseName;
    }

    public String getBodyPart() {
        return BodyPart;
    }

    public String getExerciseDescription() {
        return ExerciseDescription;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setExerciseName(String exerciseName) {
        ExerciseName = exerciseName;
    }

    public void setBodyPart(String bodyPart) {
        BodyPart = bodyPart;
    }

    public void setExerciseDescription(String exerciseDescription) {
        ExerciseDescription = exerciseDescription;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
