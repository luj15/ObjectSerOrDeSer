import org.roaringbitmap.RoaringBitmap;

import java.io.*;

/**
 * @program: Common Util
 * @description: Tool will help you ser or deser bitmap object
 * @author: Luyupeng
 * @create: 2019-08-07 10:56
 **/
public class BitMapSerOrDeser {
    /**
     * 将bitmap序列化成为字符串
     *
     * @param bitmap bitmap对象。
     * @return string 字符串
     */

    public static String serialize(RoaringBitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        DataOutputStream out = new DataOutputStream(byteArrayOutputStream);
        try {
            bitmap.serialize(out);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (byteArray == null || byteArray.length < 1)
                throw new IllegalArgumentException("this byteArray must not be null or empty");

            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < byteArray.length; i++) {
                if ((byteArray[i] & 0xff) < 0x10)
                    hexString.append("0");
                hexString.append(Integer.toHexString(0xFF & byteArray[i]));
            }
            return hexString.toString().toLowerCase();
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize bitmap ", e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将String反序列化成为bitmap对象
     *
     * @param str bitmap序列化后的字符串。
     * @return bitmap RoaringBitmap对象
     */
    public static RoaringBitmap getBitMapObject(String str) throws IOException {

        RoaringBitmap bitmap = new RoaringBitmap();
        byte[] bytes = StringUtil.toByteArray(str);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        DataInputStream in = new DataInputStream(byteArrayInputStream);
        bitmap.deserialize(in);
        in.close();
        byteArrayInputStream.close();
        return bitmap;
    }


}