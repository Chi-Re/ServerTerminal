package server.terminal.io.files;

import java.io.Closeable;
import java.io.DataOutput;
import java.io.IOException;

public class Writes implements Closeable {
    private static final Writes instance = new Writes(null);
    public DataOutput output;

    public Writes(DataOutput output) {
        this.output = output;
    }

    public static Writes get(DataOutput output) {
        instance.output = output;
        return instance;
    }

    public void l(long i) {
        try {
            this.output.writeLong(i);
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void i(int i) {
        try {
            this.output.writeInt(i);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public void b(int i) {
        try {
            this.output.writeByte(i);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public void b(byte[] array, int offset, int length) {
        try {
            this.output.write(array, 0, length);
        } catch (IOException var5) {
            throw new RuntimeException(var5);
        }
    }

    public void b(byte[] array) {
        this.b(array, 0, array.length);
    }

    public void bool(boolean b) {
        this.b(b ? 1 : 0);
    }

    public void s(int i) {
        try {
            this.output.writeShort(i);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public void f(float f) {
        try {
            this.output.writeFloat(f);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public void d(double d) {
        try {
            this.output.writeDouble(d);
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void str(String str) {
        try {
            this.output.writeUTF(str);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public void close() {
        if (this.output instanceof Closeable) {
            try {
                ((Closeable)this.output).close();
            } catch (IOException var2) {
                throw new RuntimeException(var2);
            }
        }

    }
}
