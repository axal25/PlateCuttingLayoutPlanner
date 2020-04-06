package ui.center.factory;

import ui.center.options.*;
import ui.center.options.home.HomeUI;
import ui.center.options.lab1.Lab1UI;
import ui.center.options.lab2.Lab2UI;
import ui.center.options.lambda.exp.LambdaExpUI;
import ui.center.options.mayeryn.recruit.MayerynRecruitUI;
import ui.pop.up.ErrorPopUpUI;

public class CenterFactory {

    private static AbstractCenterUI getCenter(CenterOption centerOption, String exercise) throws Exception {
        if(centerOption == null) throw new NullPointerException("Parameter CenterOption centerOption == null");
        switch (centerOption) {
            case HOME:
                return new HomeUI(exercise);

            case LABORATORY_1:
                return new Lab1UI(exercise);

            case LABORATORY_2:
                return new Lab2UI(exercise);

            case LAMBDA_EXPRESSIONS:
                return new LambdaExpUI(exercise);

            case MAYERYN_RECRUITMENT:
                return new MayerynRecruitUI(exercise);

            default:
                throw new Exception("Unsupported CenterOption: " + centerOption);
        }
    }

    public static AbstractCenterUI getSafelyCenter(CenterOption centerOption, String exercise, AbstractCenterUI fallbackCenter) {
        try {
            return getCenter(centerOption, exercise);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorPopUpUI errorPopUpUI = new ErrorPopUpUI("Error during shortening of file", e.getMessage());
            return fallbackCenter;
        }
    }
}
