package sheet;

import sheet.exceptions.SheetSizeException;

public abstract class Sheet {
    public static final int MAX_PLATE_HEIGHT = 100;
    public static final int MAX_PLATE_WIDTH = 100;

    private final int id;
    private int width, height;

    Sheet(int id, int width, int height) throws SheetSizeException {
        if(width <= 0) throw new SheetSizeException(
                this.getClass().getSimpleName(),
                "<init>",
                "Width",
                width
        );
        if(height <= 0) throw new SheetSizeException(
                this.getClass().getSimpleName(),
                "<init>",
                "Height",
                height
        );
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    private void setId() { throw new SecurityException("Not for use"); }

    public int getWidth() {
        return width;
    }

    void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    void setHeight(int height) {
        this.height = height;
    }

    public boolean isSquare() {
        return this.width == this.height;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append("{")
                .append("id=").append(id)
                .append(", width=").append(width)
                .append(", height=").append(height)
                .append("}").toString();
    }
}
