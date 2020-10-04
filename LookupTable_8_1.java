/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sam
 */
public class LookupTable_8_1 {
    int LT[]=new int[58];
    LookupTable_8_1(){
LT[0]=0;
LT[1]=1;
LT[2]=2;
LT[3]=3;
LT[4]=4;
LT[5]=6;
LT[6]=7;
LT[7]=8;
LT[8]=12;
LT[9]=14;
LT[10]=15;
LT[11]=16;
LT[12]=24;
LT[13]=28;
LT[14]=30;
LT[15]=31;
LT[16]=32;
LT[17]=48;
LT[18]=56;
LT[19]=60;
LT[20]=62;
LT[21]=63;
LT[22]=64;
LT[23]=96;
LT[24]=112;
LT[25]=120;
LT[26]=124;
LT[27]=126;
LT[28]=127;
LT[29]=128;
LT[30]=129;
LT[31]=131;
LT[32]=135;
LT[33]=143;
LT[34]=159;
LT[35]=191;
LT[36]=192;
LT[37]=193;
LT[38]=195;
LT[39]=199;
LT[40]=207;
LT[41]=223;
LT[42]=224;
LT[43]=225;
LT[44]=227;
LT[45]=231;
LT[46]=239;
LT[47]=240;
LT[48]=241;
LT[49]=243;
LT[50]=247;
LT[51]=248;
LT[52]=249;
LT[53]=251;
LT[54]=252;
LT[55]=253;
LT[56]=254;
LT[57]=255;       
    }
int getIndexofPattern(int p){
 int index=-1;
    for (int i=0;i<LT.length;i++){
     if(p==LT[i]){
     index=i;break;
     }
    }
    return index;
}
public static void main(String[] args) {
    LookupTable_8_1 lt=new LookupTable_8_1();
    int count=0;
    for(int i=0;i<256;i++){
        if(lt.getIndexofPattern(i)!=-1)count++;
        System.out.println("i="+i+"\t"+lt.getIndexofPattern(i));
    }
    System.out.println(count);
}
}
    

