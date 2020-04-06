package sheet;

import sheet.exceptions.LayoutFactoryAlreadyInitiatedException;
import sheet.exceptions.LayoutFactoryNotInitiatedException;
import sheet.exceptions.SheetSizeException;

public class StaticLayoutFactory {
    private static LayoutFactory layoutFactory = null;
    public static void initLayoutFactor(int width, int height) throws LayoutFactoryAlreadyInitiatedException, SheetSizeException {
        if(StaticLayoutFactory.layoutFactory != null) throw new LayoutFactoryAlreadyInitiatedException(
                StaticLayoutFactory.class.getSimpleName(),
                "initLayoutFactor(int width, int height)",
                "Sheet Layout Factory can only be initiated once."
        );
        StaticLayoutFactory.layoutFactory = new LayoutFactory(width, height);
    }
    public static LayoutFactory getLayoutFactory() throws LayoutFactoryNotInitiatedException {
        if(StaticLayoutFactory.layoutFactory != null) return layoutFactory;
        else throw new LayoutFactoryNotInitiatedException(
                StaticLayoutFactory.class.getSimpleName(),
                "getLayoutFactory()",
                "Sheet Layout Factory needs to be initiated before using it."
        );
    }

    // ONLY TESTING PURPOSES inside StaticLayoutFactory
    private static void resetLayoutFactory(InterfaceTestingStaticSheetFactory caller) {
        layoutFactory = null;
    }

    // ONLY FOR TESTING PURPOSES inside StaticLayoutFactory
    public interface InterfaceTestingStaticSheetFactory {
        default void resetLayoutFactory() {
            StaticLayoutFactory.resetLayoutFactory(this);
        }
    }
}
