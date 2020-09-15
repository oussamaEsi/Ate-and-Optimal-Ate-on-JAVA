package optimal_ate_pairing;

import java.math.BigInteger;

public class Outils {
    
    //Affichage du BigInteger
    public static void affichierToTabBigInteger32Bit(String titre,BigInteger big){
        BigInteger[] bigT = binnaryToBigInteger(big);
        for(int i=0;i<bigT.length;i++){
            System.out.println(titre+"["+i+"]="+bigT[i].toString(16)+";");}
    }

    public static BigInteger[] binnaryToBigInteger(BigInteger big){
        String[] tab = getBinnaryPacket32(big);
        BigInteger[] b=new BigInteger[tab.length];
        for(int k=0;k<tab.length;k++){
            b[k]=new BigInteger("0");
            int nbrBit=tab[k].length();
            int j=0;
            for(int i=nbrBit-1;i>=0;i--){
                if(String.valueOf(tab[k].charAt(i)).equals("1"))
                b[k]= b[k].add(new BigInteger("2").pow(j));
                j++;
            }     
        }
        return b;
    }

    public static String[] getBinnaryPacket32(BigInteger big){
        int nbrPacket=(int)(big.bitLength()/32);
        if(big.bitLength()%32!=0)nbrPacket++;
        String[] binnary=new String[nbrPacket];int n=nbrPacket-1;
        for(int i=nbrPacket-1;i>=0;i--)binnary[i]="";
            for(int i=big.bitLength()-1;i>=0;i--){
                binnary[n]+=getBitPosition(i,big);
                if(i!=0&&i%32==0)n--;
            }
        return binnary;
    }

    public static String getBitPosition(int index,BigInteger big){
        if(big.testBit(index))
            return "1";
        return "0";
    }
    
}
