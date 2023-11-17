package server.terminal.io.files;

import java.io.Closeable;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;

public class Reads implements Closeable {
    private static final Reads instance = new Reads(null);
    public DataInput input;

    public Reads(DataInput input) {
        this.input = input;
    }

    public static Reads get(DataInput input) {
        instance.input = input;
        return instance;
    }

    public int checkEOF() {
        try {
            return this.input instanceof InputStream ? ((InputStream)this.input).read() : -1;
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public long l() {
        try {
            return this.input.readLong();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public int i() {
        try {
            return this.input.readInt();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public short s() {
        try {
            return this.input.readShort();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public int us() {
        try {
            return this.input.readUnsignedShort();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public byte b() {
        try {
            return this.input.readByte();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public byte[] b(int length) {
        try {
            byte[] array = new byte[length];
            this.input.readFully(array);
            return array;
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public byte[] b(byte[] array) {
        try {
            this.input.readFully(array);
            return array;
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public byte[] b(byte[] array, int offset, int length) {
        try {
            this.input.readFully(array, offset, length);
            return array;
        } catch (IOException var5) {
            throw new RuntimeException(var5);
        }
    }

    public int ub() {
        try {
            return this.input.readUnsignedByte();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public boolean bool() {
        try {
            return this.input.readBoolean();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public float f() {
        try {
            return this.input.readFloat();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public double d() {
        try {
            return this.input.readDouble();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public String str() {
        try {
            return this.input.readUTF();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public void skip(int amount) {
        try {
            this.input.skipBytes(amount);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public void close() {
        if (this.input instanceof Closeable) {
            try {
                ((Closeable)this.input).close();
            } catch (IOException var2) {
                throw new RuntimeException(var2);
            }
        }

    }
}
