// 
// Decompiled by Procyon v0.6.0
// 

package net.minecraft.network.rcon;

import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.io.BufferedInputStream;
import java.net.Socket;

public class RConThreadClient extends RConThreadBase
{
    private boolean field_72657_g;
    private Socket field_72659_h;
    private byte[] field_72660_i;
    private String field_72658_j;
    
    RConThreadClient(final IServer par1IServer, final Socket par2Socket) {
        super(par1IServer);
        this.field_72660_i = new byte[1460];
        this.field_72659_h = par2Socket;
        try {
            this.field_72659_h.setSoTimeout(0);
        }
        catch (final Exception var4) {
            this.field_72619_a = false;
        }
        this.field_72658_j = par1IServer.func_71330_a("rcon.password", "");
        this.func_72609_b("Rcon connection from: " + par2Socket.getInetAddress());
    }
    
    public void run() {
        try {
            while (this.field_72619_a) {
                final BufferedInputStream bufferedinputstream = new BufferedInputStream(this.field_72659_h.getInputStream());
                final int i = bufferedinputstream.read(this.field_72660_i, 0, 1460);
                if (10 <= i) {
                    final byte b0 = 0;
                    final int j = RConUtils.func_72665_b(this.field_72660_i, 0, i);
                    if (j != i - 4) {
                        return;
                    }
                    int k = b0 + 4;
                    final int l = RConUtils.func_72665_b(this.field_72660_i, k, i);
                    k += 4;
                    final int i2 = RConUtils.func_72662_b(this.field_72660_i, k);
                    k += 4;
                    switch (i2) {
                        case 2: {
                            if (this.field_72657_g) {
                                final String s = RConUtils.func_72661_a(this.field_72660_i, k, i);
                                try {
                                    this.func_72655_a(l, this.field_72617_b.func_71252_i(s));
                                }
                                catch (final Exception exception) {
                                    this.func_72655_a(l, "Error executing: " + s + " (" + exception.getMessage() + ")");
                                }
                                continue;
                            }
                            this.func_72656_f();
                            continue;
                        }
                        case 3: {
                            final String s2 = RConUtils.func_72661_a(this.field_72660_i, k, i);
                            final int j2 = k + s2.length();
                            if (0 != s2.length() && s2.equals(this.field_72658_j)) {
                                this.field_72657_g = true;
                                this.func_72654_a(l, 2, "");
                                continue;
                            }
                            this.field_72657_g = false;
                            this.func_72656_f();
                            continue;
                        }
                        default: {
                            this.func_72655_a(l, String.format("Unknown request %s", Integer.toHexString(i2)));
                            continue;
                        }
                    }
                }
            }
        }
        catch (final SocketTimeoutException sockettimeoutexception) {}
        catch (final IOException ioexception) {}
        catch (final Exception exception2) {
            System.out.println(exception2);
        }
        finally {
            this.func_72653_g();
        }
    }
    
    private void func_72654_a(final int par1, final int par2, final String par3Str) throws IOException {
        final ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(1248);
        final DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
        final byte[] abyte = par3Str.getBytes("UTF-8");
        dataoutputstream.writeInt(Integer.reverseBytes(abyte.length + 10));
        dataoutputstream.writeInt(Integer.reverseBytes(par1));
        dataoutputstream.writeInt(Integer.reverseBytes(par2));
        dataoutputstream.write(abyte);
        dataoutputstream.write(0);
        dataoutputstream.write(0);
        this.field_72659_h.getOutputStream().write(bytearrayoutputstream.toByteArray());
    }
    
    private void func_72656_f() throws IOException {
        this.func_72654_a(-1, 2, "");
    }
    
    private void func_72655_a(final int par1, String par2Str) throws IOException {
        int j = par2Str.length();
        do {
            final int k = (4096 <= j) ? 4096 : j;
            this.func_72654_a(par1, 0, par2Str.substring(0, k));
            par2Str = par2Str.substring(k);
            j = par2Str.length();
        } while (0 != j);
    }
    
    private void func_72653_g() {
        if (null != this.field_72659_h) {
            try {
                this.field_72659_h.close();
            }
            catch (final IOException ioexception) {
                this.func_72606_c("IO: " + ioexception.getMessage());
            }
            this.field_72659_h = null;
        }
    }
}
