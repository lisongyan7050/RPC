package org.lisongyan.rpc.remote.utils;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Slf4j
public class HessianUtils {

    public static Object parseObject(byte[] data) {
        try {
            // 使用 Hessian 进行反序列化
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            Hessian2Input hi = new Hessian2Input(bis);
            Object o = hi.readObject();
            hi.close();
            bis.close();
            return o;
        }catch (Exception e){
            log.info("需要打异常");
            throw new RuntimeException(e);
        }
    }

    public static byte[] toBytes(Object o) {
        try{
            // 使用 Hessian 进行序列化
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Hessian2Output output = new Hessian2Output(os);
            output.writeObject(o);
            output.close();
            byte[] data = os.toByteArray();
            os.close();
            return data;
        }catch (Exception e){
            log.info("需要打异常");
            throw new RuntimeException(e);
        }
    }
}
