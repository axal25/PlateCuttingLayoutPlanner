package sheet;

import sheet.exceptions.SheetSizeException;

public class Layout extends Sheet {

    Layout(int id, int width, int height) throws SheetSizeException {
        super(id, width, height);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{")
                .append("id=").append(super.getId())
                .append(", width=").append(super.getWidth())
                .append(", height=").append(super.getHeight())
                .append("}").toString();
    }
}
