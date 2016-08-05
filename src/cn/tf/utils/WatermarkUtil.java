package cn.tf.utils;
import java.awt.Color;  
import java.awt.Font;  
import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.FileOutputStream;  
import javax.imageio.ImageIO;  
    
/** 
 *   java给图片添加文字水印 
 */  
public class WatermarkUtil {  
   /** 
    * 添加水印 
    * @param srcImgPath 需要添加水印的图片的路径 
    * @param outImgPath 添加水印后图片输出路径 
    * @param markContentColor 水印文字的颜色 
    * @param waterMarkContent 水印的文字 
    */  
    public boolean mark(String srcImgPath, String outImgPath, Color markContentColor, String waterMarkContent) {  
        try {  
            // 读取原图片信息  
            File srcImgFile = new File(srcImgPath);  
            Image srcImg = ImageIO.read(srcImgFile);  
            int srcImgWidth = srcImg.getWidth(null);    //确定图像的宽度
            int srcImgHeight = srcImg.getHeight(null);  //确定图像的高度
            // 加水印  
            
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);  
           
            // Graphics2D 类扩展 Graphics 类，以提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制。
            // 它是用于在 Java 平台上呈现二维形状、文本和图像的基础类。 
            //由于 Graphics2D 是一个抽象类，无法直接创建 Graphics2D 对象。
            //Graphics2D 对象必须从另一个 Graphics2D 对象获得或者从 BufferedImage 之类的图像对象获得。 
            Graphics2D g = bufImg.createGraphics();    //创建供绘制闭屏图像（off-screen image）使用的图形上下文 
            
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);  
            
            int size=14;
            if(srcImgWidth>1000){
            	size=60;
            }else if(srcImgWidth>500){
            	size=40;
            }else if(srcImgWidth<=200){
            	size=12;
            }
      
            
            Font font = new Font("微软雅黑", Font.PLAIN, size);    
            g.setColor(markContentColor); //设置水印颜色    
            g.setFont(font);  
            int x = srcImgWidth - getWatermarkLength(waterMarkContent, g) - 3;  
            int y = srcImgHeight - 3;   
            g.drawString(waterMarkContent, x, y);  
            g.dispose();  
            
            
            // 输出图片  
            FileOutputStream outImgStream = new FileOutputStream(outImgPath);  
            ImageIO.write(bufImg, "jpg", outImgStream);  
            outImgStream.flush();  
            outImgStream.close(); 
            return true;
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return false;
    }  
      
    /** 
     * 获取水印文字总长度 
     * @param waterMarkContent 水印的文字 
     * @param g 
     * @return 水印文字总长度 
     */  
    public int getWatermarkLength(String waterMarkContent, Graphics2D g) {  
    			//Graphics.getFontMetrics(Font f) 获取指定字体的字体规格。
    										//charsWidth()返回显示此 Font中指定字符数组的总advance width,advance 是字符串基线上最左边的点到最右边的点之间的距离。
    												// 三个参数：  1.测量的字符数组   2.数组中字符的起始偏移量     3.数组中要测量的字符数 
    	return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());  
    }  
} 