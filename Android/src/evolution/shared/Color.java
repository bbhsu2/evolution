package evolution.shared;

import com.allgoodpeopleus.evolution.EvolutionModel;

public class Color {

    public static final Color Purple = FromHex(0xB455B6);
    public static final Color Blue = FromHex(0x44E61C);

    public double R, G, B;
    
    public String hexVal;
    public String getHexval(){
    	return hexVal;
    }
    
    String binVal;
    public String getBinVal() {
        return binVal;
    }

    public int getFitness(){
        return Utils.GetFitness(this, EvolutionModel.GA_TARGET);
    }

    public void setBinVal(String binVal){
        if(binVal.length() == 24){
            this.binVal = binVal;
        } else{
            int len = binVal.length();
            char[] buffer = new char[24 - len];
            for(int i = 0; i < buffer.length; i++) buffer[i] = '0';
            this.binVal = new String(buffer) + binVal;
        }
    }

    public Color(int r, int g, int b, String hexVal, String binVal) {
        this.R = r;
        this.G = g;
        this.B = b;
        this.hexVal = hexVal;
        setBinVal(binVal);
    }

    public static int GetAndroidColor(Color color){
        return android.graphics.Color.argb(255, (int)color.R, (int)color.G, (int)color.B);
    }
    
    public static Color ConvertIntToColor(int intColor){
    	String hexColor = String.format("#%06X", (0xFFFFFF & intColor));
    	return FromHex((int)Long.parseLong(hexColor, 16));
    }

    private static int at(int offset, int hex){
        return (hex >> offset) & 0xFF;
    }
    private static String str(int value){
        return Integer.toHexString(value);
    }
    private static String bin(String hex){
        int i = Integer.parseInt(hex, 16);
        return Integer.toBinaryString(i);
    }

    public static Color FromHex(int hex){
        int r = at(16, hex);// / 255;
        int g = at(8,hex);/// 255;
        int b = at(0,hex);/// 255;

        String rgbString = str(r) + str(g) + str(b);

        return new Color(r,g,b,rgbString, bin(rgbString));
    }

    public static Color FromBin(String bin){
        int i = Integer.parseInt(bin, 2);
        return FromHex(i);
    }

}
