package Test;

    public class Block {
        private int[][] shape;
        private int color;

        public Block(int[][] shape, int color) {
            this.shape = shape;
            this.color = color;
        }

        public int getShape(int i, int j) {
            return shape[j][i];
        }

        public int width() {
            return shape[0].length;
        }

        public int height() {
            return shape.length;
        }
        public void rotate() {
            int n = shape.length;
            int m = shape[0].length;
            int[][] newShape = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    newShape[i][j] = shape[n - 1 - j][i];
                }
            }
            shape = newShape;
        }
    }
