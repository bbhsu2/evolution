/*
 * This code is licensed under the terms of the MIT license
 *
 * Copyright (C) 2014 All Good People LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal 
 * in the Software without restriction, including without limitation the rights 
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all 
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION 
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

using System;

#if __IOS__
using MonoTouch.UIKit;
using MonoTouch.CoreGraphics;
#endif

namespace Evolution.Shared
{
    public class Color
    {
        public double R, G, B;
        public string hexVal;

        string _binVal;
        public string binVal
        {
            get { return _binVal; }
            set {
                if (value.Length == 24)
                    _binVal = value;
                else  {
                    int len = value.Length;
                    char[] buffer = new char[24 - len];
                    for (int i = 0; i < buffer.Length; i++) buffer[i] = '0';
                    _binVal = new string(buffer) + value;
                }
            }
        }
        #region samples
        public static readonly Color Purple = 0xB455B6;
        public static readonly Color LightPurple = 0xA268C4;
        public static readonly Color Blue = 0x3498DB;
        public static readonly Color DarkBlue = 0x2C3E50;
        public static readonly Color Green = 0x77D065;
        public static readonly Color LightGreen = 0x8EEC66;
        public static readonly Color DarkGreen = 0x0A9B05;
        public static readonly Color Gray = 0x738182;
        public static readonly Color LightGray = 0xB4BCBC;
        public static readonly Color LightBrown = 0xF7C563;
        public static readonly Color Peach = 0xFBE6D3;
        public static readonly Color DullPeach = 0xF2D4B9;
        public static readonly Color LightRed = 0xFA4646;
        public static readonly Color DarkRed = 0xA11F35;
        public static readonly Color EvoGreen = 0x44B542;
        #endregion
        public int fitness { get { return Utils.GetFitness(this, Model.GA_TARGET); } }

        public static Color FromHex(int hex)
        {
            Func<int, int> at = offset => (hex >> offset) & 0xFF;
            Func<int, string> str = value => value.ToString("X2");
            Func<string, string> bin = value => Convert.ToString(Convert.ToInt32(value, 16), 2);

            int r = at(16);
            int g = at(8);
            int b = at(0);

            string rgbString = str(r) + str(g) + str(b);

            return new Color
            {
                R = r / 255.0,
                G = g / 255.0,
                B = b / 255.0,
                hexVal = rgbString,
                binVal = bin(rgbString)
            };
        }

        public static Color FromBin(string bin)
        {
            string hex = Convert.ToInt32(bin, 2).ToString("X2");
            return FromHex(int.Parse(hex, System.Globalization.NumberStyles.AllowHexSpecifier));
        }

        public static implicit operator Color(int hex)
        {
            return FromHex(hex);
        }

        public static implicit operator Color(string bin)
        {
            return FromBin(bin);
        }

        public static implicit operator int(Color color)
        {
            return ColorValue(color);
        }

        //casting Color to string gives you binary number as string
        //useful when converting to a generalized GA lib combined with the Hello World demo
        public static implicit operator string(Color color)
        {
            return color.binVal;
        }

        public static int ColorValue(Color color)
        {
            return int.Parse(color.hexVal, System.Globalization.NumberStyles.AllowHexSpecifier);
        }

#if __IOS__
        public UIColor ToUIColor()
        {
            return UIColor.FromRGB((float)R, (float)G, (float)B);
        }

        public static implicit operator UIColor(Color color)
        {
            return color.ToUIColor();
        }

        public static implicit operator CGColor(Color color)
        {
            return color.ToUIColor().CGColor;
        }

        public static Color FromUIColor(UIColor uicolor)
        {
            float r, g, b, a;
            string hexString;
            Func<int, string> str = value => value.ToString("X2");
            Func<string, string> bin = value => Convert.ToString(Convert.ToInt32(value, 16), 2);

            uicolor.GetRGBA(out r, out g, out b, out a);
            r *= 255.0F;
            g *= 255.0F;
            b *= 255.0F;
            hexString = str((int)r) + str((int)g) + str((int)b);

            return new Color {
                R = r,
                G = g,
                B = b,
                hexVal = hexString,
                binVal = bin(hexString)
            };
        }
#endif
    }
}
