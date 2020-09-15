package optimal_ate_pairing;

import java.math.BigInteger;

public class Operations_Fpk {
    
    private BigInteger ZERO    = new BigInteger("0");
    private BigInteger ONE     = new BigInteger("1");
    public BigInteger TWO     = new BigInteger("2");
    private BigInteger THREE   = new BigInteger("3");
    private BigInteger FOUR    = new BigInteger("4");
    private BigInteger FIVE    = new BigInteger("5");
    private BigInteger SIX     = new BigInteger("6");
    private BigInteger EIGHT   = new BigInteger("8");
    private BigInteger TWELEVE = new BigInteger("12");
    private BigInteger SIXTEEN = new BigInteger("16");
    
    BigInteger p = new BigInteger("2370fb049d410fbe4e761a9886e502417d023f40180000017e80600000000001",16);//256
    private BigInteger r = new BigInteger("2370fb049d410fbe4e761a9886e502411dc1af70120000017e80600000000001",16);
    private BigInteger tr = new BigInteger("5f408fd0060000000000000000000001",16);//tr
    
    BigInteger t = new BigInteger("3fc0100000000000",16); //4593689212103950336
    private BigInteger s = t.multiply(SIX).add(TWO); //1 7e806000 00000002
    BigInteger t2 = t.pow(2);
    BigInteger t3 = t.pow(3);
    private int L = 65;
    
    private int Taille_p = 256;
    private BigInteger base = TWO.pow(32);          
    public BigInteger R = (TWO.pow(Taille_p+32)).mod(p); // R=B(n) / B: base / n: nmbr de paquets
    public BigInteger R2 = R.pow(2).mod(p);
    public BigInteger InvR = R.modInverse(p);
    private BigInteger Np = (p.negate()).modInverse(base); // N'=(-p)(-1)mod(base)
    public BigInteger u = p.subtract(FIVE);// Xk-u
  
    private BigInteger ONETWO = TWO.modInverse(p);
    private BigInteger ONETHREE = THREE.modInverse(p);
    private BigInteger ONESIX = SIX.modInverse(p);
    
    private BigInteger NONE = p.subtract(ONE).mod(p); 
    private BigInteger NTWO = p.subtract(TWO).mod(p);
      
    private BigInteger Ey11 = p.subtract(ONE).divide(TWELEVE).mod(p);
    private BigInteger Ey12 = p.subtract(ONE).divide(SIX).mod(p);
    private BigInteger Ey13 = p.subtract(ONE).divide(FOUR).mod(p);
    private BigInteger Ey14 = p.subtract(ONE).divide(THREE).mod(p);
    private BigInteger Ey15 = Ey11.multiply(FIVE).mod(p);
    
    public BigInteger y11 = u.modPow(Ey11,p); 
    public BigInteger y12 = u.modPow(Ey12,p);
    public BigInteger y13 = u.modPow(Ey13,p);
    public BigInteger y14 = u.modPow(Ey14,p);
    public BigInteger y15 = u.modPow(Ey15,p);
    
    public BigInteger y21 = y11.multiply(y11).mod(p); 
    public BigInteger y22 = y12.multiply(y12).mod(p);
    public BigInteger y23 = y13.multiply(y13).mod(p);
    public BigInteger y24 = y14.multiply(y14).mod(p);
    public BigInteger y25 = y15.multiply(y15).mod(p);
    
    public BigInteger y31 = y11.multiply(y21).mod(p); 
    public BigInteger y32 = y12.multiply(y22).mod(p);
    public BigInteger y33 = y13.multiply(y23).mod(p);
    public BigInteger y34 = y14.multiply(y24).mod(p);
    public BigInteger y35 = y15.multiply(y25).mod(p);
    
    public BigInteger exp = p.pow(12).subtract(ONE).divide(r); // exp = (p(12)-1)/r
    
    
    // Multiplication Naive
    public BigInteger[] multiplicationNaive(BigInteger[] a, BigInteger[] b, int k){
        BigInteger[] c = new BigInteger[k];
        BigInteger[] cc = new BigInteger[2*k-1]; for(int init=0; init<(2*k-1); init++) cc[init]=ZERO;
        BigInteger[] aa = new BigInteger[2*k-1]; for(int init=0; init<(2*k-1); init++) aa[init]=ZERO; for(int init=0; init<k; init++) aa[init]=a[init];
        BigInteger[] bb = new BigInteger[2*k-1]; for(int init=0; init<(2*k-1); init++) bb[init]=ZERO; for(int init=0; init<k; init++) bb[init]=b[init];
    
        for (int i=0; i<(2*k-1); i++){
            for (int j=0; j<=i; j++){
                cc[i]= cc[i].add(aa[j].multiply(bb[i-j]).mod(p)).mod(p);
            }
        }
        
        //Reduction
        for(int i=0; i<k-1; i++){
            c[i]= cc[i].add((cc[i+k].multiply(u)).mod(p)).mod(p);
        }
        c[k-1]=cc[k-1];
        
        return c;
    }    
    
    // Multiplication in Fp2
    public BigInteger[] karatsuba2( BigInteger[] a, BigInteger[] b, int k ){
        BigInteger[] c = new BigInteger[2];
        BigInteger[] cc = new BigInteger[3];
        
        BigInteger c0 = a[0].multiply(b[0]).mod(p);
        BigInteger c2 = a[1].multiply(b[1]).mod(p);

        BigInteger c1 = (((a[0].add(a[1])).multiply(b[0].add(b[1]))).subtract(c0)).subtract(c2).mod(p);
        
        //Reduction
        if( k==2 ){
            c[0] = c0.add((c2.multiply(u)).mod(p)).mod(p);
            c[1]=c1;
            return c;
        }
        else {
            cc[0] = c0; cc[1] = c1; cc[2] = c2;
            return cc;
        }
    }
    
    // Karatsuba General
    public BigInteger[] karatsubaGeneral( BigInteger[] a, BigInteger[] b, int k, int taille ){
        
        BigInteger[] c = new BigInteger[taille];    
        if ( taille==1 ){
            c[0] = a[0].multiply(b[0]).mod(p); 
        }
        
        if( taille==2 ){   
            c = karatsuba2(a,b,k); 
        }
        
        if( taille>2 ){
            int taille1 = (int) taille/2; int taille2 = taille-taille1;
            //System.out.println("taille1 = "+taille1); System.out.println("taille2 = "+taille2);
            
            BigInteger[] a1 = new BigInteger[taille1]; for(int i=0; i<a1.length; i++) a1[i] = a[i];
            BigInteger[] a2 = new BigInteger[taille2]; for(int i=0; i<a2.length; i++) a2[i] = a[i+taille1];
                
            BigInteger[] b1 = new BigInteger[taille1]; for(int i=0; i<b1.length; i++) b1[i] = b[i];
            BigInteger[] b2 = new BigInteger[taille2]; for(int i=0; i<b2.length; i++) b2[i] = b[i+taille1];
                
            BigInteger[] c0 = new BigInteger[(2*taille1)-1]; for(int i=0; i<c0.length; i++) c0[i] = ZERO;
            BigInteger[] c2 = new BigInteger[(2*taille2)-1]; for(int i=0; i<c2.length; i++) c2[i] = ZERO;
            BigInteger[] c1 = new BigInteger[(2*taille2)-1]; for(int i=0; i<c1.length; i++) c1[i] = ZERO;
                
            c0 = karatsubaGeneral( a1, b1, k, taille1); //for(int i=0;i<c0.length;i++) System.out.println("c0 = "+c0[i].toString(16));
            c2 = karatsubaGeneral( a2, b2, k, taille2); //for(int i=0;i<c2.length;i++) System.out.println("c2 = "+c2[i].toString(16));
            
            BigInteger[] tmp1 = new BigInteger[taille2]; for(int i=0; i<tmp1.length; i++) tmp1[i] = ZERO;
            BigInteger[] tmp2 = new BigInteger[taille2]; for(int i=0; i<tmp2.length; i++) tmp2[i] = ZERO;
            
            for (int i=0; i<tmp1.length; i++){
                if( i<a1.length ) tmp1[i] = a1[i].add(a2[i]).mod(p);
                else tmp1[i] = a2[i];
            }
            for (int i=0; i<tmp2.length; i++){
                if( i<b1.length ) tmp2[i] = b1[i].add(b2[i]).mod(p);
                else tmp2[i] = b2[i];
            }
            c1 = karatsubaGeneral( tmp1, tmp2, k, taille2);

            for (int i=0; i<c0.length; i++){
                c1[i] = c1[i].subtract(c0[i]).mod(p);
            }
            
            for (int i=0; i<c2.length; i++)
                c1[i] = c1[i].subtract(c2[i]).mod(p);
            // Fin calcul de c1
            //for(int i=0; i<c1.length; i++) System.out.println("c1 = "+c1[i].toString(16));
            
            //Reduction
            BigInteger[] cc1 = new BigInteger[c1.length+taille1]; for(int i=0; i<cc1.length; i++) cc1[i] = ZERO;
            //System.out.println("taille cc1 = "+cc1.length);
            cc1 = decalageAvecZero(c1,taille1); //for(int i=0; i<cc1.length; i++) System.out.println("cc1 = "+cc1[i].toString(16));
            //Reduction de cc1
            for(int i=k; i<cc1.length; i++){
                cc1[i-k]= cc1[i].multiply(u).mod(p);
            }
            //for(int i=0; i<cc1.length; i++) System.out.println("cc1 = "+cc1[i].toString(16));
            
            BigInteger[] cc2 = new BigInteger[c2.length+(taille1*2)]; for(int i=0; i<cc2.length; i++) cc2[i] = ZERO;
            //System.out.println("taille cc2 = "+cc2.length);
            cc2 = decalageAvecZero(c2,taille1*2); //for(int i=0; i<cc2.length; i++) System.out.println("cc2 = "+cc2[i].toString(16));
            //Reduction de cc2
            for(int i=k; i<cc2.length; i++){
                cc2[i-k]= cc2[i].multiply(u).mod(p);
            }
            //for(int i=0; i<cc2.length; i++) System.out.println("cc2 = "+cc2[i].toString(16));
            
            for(int i=0; i<k; i++){
                if(k<cc1.length){
                    if( i<c0.length ) c[i] = (c0[i].add(cc1[i]).mod(p)).add(cc2[i]).mod(p);
                    else c[i] = cc1[i].add(cc2[i]).mod(p);
                }
            }    
        }
        return c;
    }
    
    // Decalage d'un tableau adroit avec des zeros
    private BigInteger[] decalageAvecZero(BigInteger[] a, int nombre){
        BigInteger[] resultat = new BigInteger[a.length+nombre]; for(int i=0; i<resultat.length; i++) resultat[i] = ZERO;
        for(int i=0; i<a.length; i++){
            resultat[resultat.length-i-1] = a[a.length-i-1];
        }
        return resultat;
    }
    
    // Multiplication Toom Cook 3
    public BigInteger[] toomCook3(BigInteger[] a, BigInteger[] b){ 
        BigInteger[] c = new BigInteger[3];
        BigInteger[] aa= new BigInteger[5];
        BigInteger[] bb= new BigInteger[5];
        BigInteger[] cc= new BigInteger[5];
        BigInteger[] ccc= new BigInteger[5];
        BigInteger tmp1=ZERO, tmp2=ZERO;

        aa[0] = a[0]; 
        tmp1 = a[0].add(a[2]).mod(p);
        aa[1] = tmp1.add(a[1]).mod(p);
        aa[2] = tmp1.subtract(a[1]).mod(p);
        aa[3] = a[0].add(a[1].multiply(TWO)).add(a[2].multiply(FOUR)).mod(p);
        aa[4] = a[2];
        
        bb[0] = b[0]; 
        tmp2 = b[0].add(b[2]).mod(p);
        bb[1] = tmp2.add(b[1]).mod(p);
        bb[2] = tmp2.subtract(b[1]).mod(p);
        bb[3] = b[0].add(b[1].multiply(TWO)).add(b[2].multiply(FOUR)).mod(p);
        bb[4] = b[2];
        
        cc[0] = aa[0].multiply(bb[0]).mod(p);
        cc[1] = aa[1].multiply(bb[1]).mod(p);
        cc[2] = aa[2].multiply(bb[2]).mod(p);
        cc[3] = aa[3].multiply(bb[3]).mod(p);
        cc[4] = aa[4].multiply(bb[4]).mod(p); 

        ccc[0] = cc[0];
        ccc[1] = cc[1].subtract(ccc[0]).mod(p);
        ccc[2] = ((cc[2].subtract(ccc[0]).mod(p)).add(ccc[1]).mod(p)).multiply(ONETWO).mod(p);
        ccc[3] = ((cc[3].subtract(ccc[0]).mod(p)).multiply(ONESIX).mod(p)).subtract((ccc[1].add(ccc[2]).mod(p)).multiply(ONETHREE).mod(p)).mod(p);
        ccc[4] = cc[4];         
        
        cc[0] = ccc[0];
        cc[1] = ((ccc[1].subtract(ccc[2]).mod(p)).subtract(ccc[3]).mod(p)).add((ccc[4].multiply(TWO)).mod(p)).mod(p);
        cc[2] = (ccc[2].subtract(ccc[4])).mod(p);
        cc[3] = (ccc[3].subtract((ccc[4].multiply(TWO)).mod(p))).mod(p);
        cc[4] = ccc[4];
       
        //Reduction
        c[0] = cc[0].add(cc[3].multiply(u).mod(p)).mod(p); 
        c[1] = cc[1].add(cc[4].multiply(u).mod(p)).mod(p);
        c[2] = cc[2];
        
        return c;
    }

    /// *** Arithmetic over Fp2
    // Addition dans Fp2
    public BigInteger[] addFp2 ( BigInteger[] a, BigInteger[] b){
        BigInteger[] c = new BigInteger[2];
        c[0] = a[0].add(b[0]).mod(p);
        c[1] = a[1].add(b[1]).mod(p);
        return c;
    }
    
    // Substraction dans Fp2
    public BigInteger[] subFp2 ( BigInteger[] a, BigInteger[] b){
        BigInteger[] c = new BigInteger[2];
        c[0] = a[0].subtract(b[0]).mod(p);
        c[1] = a[1].subtract(b[1]).mod(p);
        return c;
    }
    
    // Multiplication by constante bo dans Fp2
    public BigInteger[] mulCFp2 ( BigInteger[] a, BigInteger b){
        BigInteger[] c = new BigInteger[2];
        c[0] = a[0].multiply(b).mod(p);
        c[1] = a[1].multiply(b).mod(p);
        return c;
    }
    
    // Squaring in Fp2
    public BigInteger[] squarFp2 ( BigInteger[] a ){
        BigInteger[] c = new BigInteger[2];
        BigInteger v0 = a[0].multiply(a[1]).mod(p);
        c[0] = (a[0].add(a[1]).multiply(a[0].add(a[1].multiply(u))).subtract(v0).subtract(v0.multiply(u))).mod(p);
        c[1] = v0.multiply(TWO).mod(p);
        return c;
    }    
    
    // Inversion dans Fp2
    public BigInteger[] invFp2 ( BigInteger[] a ){
        BigInteger[] c = new BigInteger[2];
        BigInteger t0, t1;
        t0 = a[0].modPow(TWO, p);
        t1 = a[1].modPow(TWO, p);        
        t0 = t0.subtract(t1.multiply(u)).mod(p);
        t1 = t0.modInverse(p); //t1=t0(-1)
        c[0] = a[0].multiply(t1).mod(p);
        c[1] = a[1].negate().multiply(t1).mod(p);
        return c;
    }
    
    // Multiplication by u (Reduction)
    private BigInteger[] mulByu( BigInteger[] a){
        BigInteger[] b = new BigInteger[2];
        b[0] = a[1].multiply(u).mod(p);
        b[1] = a[0];
        return b;
    }
    
    // Conjugate in Fp2
    private BigInteger[] conjugate( BigInteger[] a){
        BigInteger[] b = new BigInteger[2];
        b[0] = a[0];
        b[1] = p.subtract(a[1]).mod(p);
        return b;
    }
    
    /// *** Arithmetic over Fp4
    //Addition in Fp4
    public BigInteger[][] addFp4( BigInteger[][] a, BigInteger[][] b ){
        BigInteger[][] c = new BigInteger[2][2];
        c[0] = addFp2(a[0],b[0]);
        c[1] = addFp2(a[1],b[1]);
        return c;
    }
    
    //Substraction in Fp4
    public BigInteger[][] subFp4( BigInteger[][] a, BigInteger[][] b ){
        BigInteger[][] c = new BigInteger[2][2];
        c[0] = subFp2(a[0],b[0]);
        c[1] = subFp2(a[1],b[1]);
        return c;
    }
    
    // Conjugate in Fp4
    private BigInteger[][] conjugate_Fp4( BigInteger[][] a){
        BigInteger[][] c = new BigInteger[2][2];
        c[0][0]=a[0][0]; c[0][1]=a[0][1];
        c[1][0]=p.subtract(a[1][0]).mod(p); c[1][1]=p.subtract(a[1][1]).mod(p);
        return c;
    }
    
     //Multiplication by constatnt
    public BigInteger[][] mulCFp4( BigInteger[][] a, BigInteger b0 ){
        BigInteger[][] c = new BigInteger[2][2];
        c[0][0] = a[0][0].multiply(b0).mod(p);
        c[0][1] = a[0][1].multiply(b0).mod(p);
        c[1][0] = a[1][0].multiply(b0).mod(p);
        c[1][1] = a[1][1].multiply(b0).mod(p);

        return c;
    }
    
    // Squaring in Fp4 {algorithme 9}
    public BigInteger[][] squarFp4( BigInteger[][] a ){
        BigInteger[][] c = new BigInteger[2][2];
        BigInteger[] t0,t1 = new BigInteger[2];
        t0 = squarFp2(a[0]);
        t1 = squarFp2(a[1]);
        c[0] = mulByu(t1);
        c[0] = addFp2(c[0],t0);
        c[1] = addFp2(a[0],a[1]);
        c[1] = subFp2(subFp2(squarFp2(c[1]),t0),t1);
        //for(int i=0;i<2;i++)for(int j =0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }
    
    // Squaring in Fp4 version 2 (just pour le test)
    public BigInteger[][] squarFp4_V2 ( BigInteger[][] a ){
        BigInteger[][] c = new BigInteger[2][2];
        BigInteger[] v0 = new BigInteger[2];
        
        v0 = karatsuba2(a[0],a[1],2);
        c[0] = subFp2(subFp2(karatsuba2(addFp2(a[0],a[1]),addFp2(a[0],mulByu(a[1])),2),v0),mulByu(v0));
        c[1] = mulCFp2(v0,TWO);
        //for(int i=0;i<2;i++)for(int j =0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }
    
    //Multiplication by y in Fp4
    public BigInteger[][] mulByyFp4( BigInteger[][] a ){
        BigInteger[][] c = new BigInteger[2][2];
        c[0] = mulByu(a[1]);
        c[1] = a[0];
        return c;
    } 
    
        // Multiplication in Fp2
    public BigInteger[][] multFp4( BigInteger[][] a, BigInteger[][] b ){
        BigInteger[][] c = new BigInteger[2][2];
 
        BigInteger[] c0 = new BigInteger[2];
        BigInteger[] c1 = new BigInteger[2]; 
        BigInteger[] c2 = new BigInteger[2];
        
        c0 = karatsuba2(a[0],b[0],2);
        c2 = karatsuba2(a[1],b[1],2);

        c1 = subFp2(subFp2(karatsuba2(addFp2(a[0],a[1]),addFp2(b[0],b[1]),2),c0),c2);
        c0 = addFp2(c0,mulByu(c2));
        
        c[0]=c0; c[1]=c1;
        //for(int i=0;i<2;i++)for(int j =0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }
    
    /// *** Arithmetic over Fp6
    //Addition in Fp6
    public BigInteger[][] addFp6( BigInteger[][] a, BigInteger[][] b ){
        BigInteger[][] c = new BigInteger[3][2];
        c[0] = addFp2(a[0],b[0]);
        c[1] = addFp2(a[1],b[1]);
        c[2] = addFp2(a[2],b[2]);
        return c;
    }
    
    //Substarction in Fp6
    public BigInteger[][] subFp6( BigInteger[][] a, BigInteger[][] b ){
        BigInteger[][] c = new BigInteger[3][2];
        c[0] = subFp2(a[0],b[0]);
        c[1] = subFp2(a[1],b[1]);
        c[2] = subFp2(a[2],b[2]);
        return c;
    }
    
    //Multiplication by y in Fp6
    public BigInteger[][] mulByy( BigInteger[][] a ){
        BigInteger[][] c = new BigInteger[3][2];
        c[0] = mulByu(a[2]);
        c[1] = a[0];
        c[2] = a[1];
        return c;
    }    
    
    //Multiplication in Fp6
    public BigInteger[][] mulFp6( BigInteger[][] a, BigInteger[][] b ){
        BigInteger[][] c = new BigInteger[3][2];
        BigInteger[][] t = new BigInteger[3][2];
        t[0] = karatsuba2(a[0],b[0],2);
        t[1] = karatsuba2(a[1],b[1],2);
        t[2] = karatsuba2(a[2],b[2],2);
        
        c[0] = addFp2(mulByu(subFp2(subFp2(karatsuba2(addFp2(a[1],a[2]),addFp2(b[1],b[2]),2),t[1]),t[2])),t[0]);
        c[1] = addFp2(subFp2(subFp2(karatsuba2(addFp2(a[0],a[1]),addFp2(b[0],b[1]),2),t[0]),t[1]),mulByu(t[2]));
        c[2] = addFp2(subFp2(subFp2(karatsuba2(addFp2(a[0],a[2]),addFp2(b[0],b[2]),2),t[0]),t[2]),t[1]);
        
        //for(int i=0;i<3;i++)for(int j =0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }
    
    //Multiplication by b0(Fp2) in Fp6
    public BigInteger[][] mulFp6_b0( BigInteger[][] a, BigInteger[] b0 ){
        BigInteger[][] c = new BigInteger[3][2];
        c[0] = karatsuba2(a[0],b0,2);
        c[1] = karatsuba2(a[1],b0,2);
        c[2] = karatsuba2(a[2],b0,2);
        //for(int i=0;i<3;i++)for(int j =0;j<2;j++) System.out.println(c[i][j].toString(16));
        
        return c;
    }
    
    //Multiplication by b0(Fp2) in Fp6
    public BigInteger[][] mulFp6_b0b1( BigInteger[][] a, BigInteger[] b0, BigInteger[] b1 ){
        BigInteger[][] c = new BigInteger[3][2];
        BigInteger[][] t = new BigInteger[2][2];
        t[0] = karatsuba2(a[0],b0,2);
        t[1] = karatsuba2(a[1],b1,2);
        c[0] = addFp2(mulByu(subFp2(karatsuba2(addFp2(a[1],a[2]),b1,2),t[1])),t[0]);
        c[1] = subFp2(subFp2(karatsuba2(addFp2(a[0],a[1]),addFp2(b0,b1),2),t[0]),t[1]);
        c[2] = addFp2(karatsuba2(a[2],b0,2),t[1]);
        
        //for(int i=0;i<3;i++)for(int j =0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }
    
    //Squaring in Fp6
    public BigInteger[][] squarFp6( BigInteger[][] a ){
        BigInteger[][] c = new BigInteger[3][2];
        BigInteger[][] t = new BigInteger[6][2];
        t[4] = mulCFp2(karatsuba2(a[0],a[1],2),TWO);
        t[5] = squarFp2(a[2]); //
        t[1] = addFp2(mulByu(t[5]),t[4]);
        t[2] = subFp2(t[4],t[5]);
        t[3] = squarFp2(a[0]); //
        t[4] = addFp2(subFp2(a[0],a[1]),a[2]);
        t[5] = mulCFp2(karatsuba2(a[1],a[2],2),TWO);
        t[4] = squarFp2(t[4]); //
        t[0] = addFp2(mulByu(t[5]),t[3]);
        t[2] = subFp2(addFp2(addFp2(t[2],t[4]),t[5]),t[3]);
        
        c[0] = t[0]; c[1] = t[1]; c[2] = t[2];
        //for(int i=0;i<3;i++)for(int j =0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }
    
    // Inversion dans Fp6
    public BigInteger[][] invFp6 ( BigInteger[][] a ){
        BigInteger[][] c = new BigInteger[3][2];
        BigInteger[][] t = new BigInteger[7][2];
        
        t[0] = squarFp2(a[0]);
        t[1] = squarFp2(a[1]);        
        t[2] = squarFp2(a[2]);
        t[3] = karatsuba2(a[0],a[1],2);
        t[4] = karatsuba2(a[0],a[2],2);
        t[5] = karatsuba2(a[2],a[1],2); // t5 <- a2.a3
        c[0] = subFp2(t[0],mulByu(t[5]));
        c[1] = subFp2(mulByu(t[2]),t[3]);
        c[2] = subFp2(t[1],t[4]);
        t[6] = karatsuba2(a[0],c[0],2);
        t[6] = addFp2(t[6],mulByu(karatsuba2(a[2],c[1],2)));
        t[6] = addFp2(t[6],mulByu(karatsuba2(a[1],c[2],2)));
        t[6] = invFp2(t[6]);
        c[0] = karatsuba2(c[0],t[6],2);
        c[1] = karatsuba2(c[1],t[6],2);
        c[2] = karatsuba2(c[2],t[6],2);
        //for(int i=0;i<3;i++)for(int j =0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }
    
    // opposite in Fp6
    private BigInteger[][] oppFp6( BigInteger[][] a){
        BigInteger[][] c = new BigInteger[3][2];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i][j] = p.subtract(a[i][j]).mod(p);
        return c;
    }
    
    /// *** Arithmetic over Fp12
    // Addition in Fp12
    public BigInteger[][] addFp12( BigInteger[][] a, BigInteger[][] b ){
        BigInteger[][] c = new BigInteger[6][2];
        BigInteger[][] c0 = new BigInteger[3][2];BigInteger[][] c1 = new BigInteger[3][2];
        BigInteger[][] a0 = new BigInteger[3][2];BigInteger[][] a1 = new BigInteger[3][2];
        BigInteger[][] b0 = new BigInteger[3][2];BigInteger[][] b1 = new BigInteger[3][2];

        for(int i=0;i<3;i++)for(int j=0;j<2;j++) a0[i][j]=a[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) a1[i][j]=a[i+3][j];   
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) b0[i][j]=b[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) b1[i][j]=b[i+3][j];
        
        c0 = addFp6(a0,b0);
        c1 = addFp6(a1,b1);
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i][j]=c0[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i+3][j]=c1[i][j];
        return c;
    }
    
    // Substraction in Fp12
    public BigInteger[][] subFp12( BigInteger[][] a, BigInteger[][] b ){
        BigInteger[][] c = new BigInteger[6][2];
        BigInteger[][] c0 = new BigInteger[3][2];BigInteger[][] c1 = new BigInteger[3][2];
        BigInteger[][] a0 = new BigInteger[3][2];BigInteger[][] a1 = new BigInteger[3][2];
        BigInteger[][] b0 = new BigInteger[3][2];BigInteger[][] b1 = new BigInteger[3][2];

        for(int i=0;i<3;i++)for(int j=0;j<2;j++) a0[i][j]=a[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) a1[i][j]=a[i+3][j];   
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) b0[i][j]=b[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) b1[i][j]=b[i+3][j];
        
        c0 = subFp6(a0,b0);
        c1 = subFp6(a1,b1);
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i][j]=c0[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i+3][j]=c1[i][j];
        return c;
    }
    
    // Conjugate Fp12
    public BigInteger[][] conjugate_Fp12( BigInteger[][] a){
        BigInteger[][] c = new BigInteger[6][2];

        c[0][0]=a[0][0]; c[0][1]=a[0][1];
        c[1][0]=a[1][0]; c[1][1]=a[1][1];
        c[2][0]=a[2][0]; c[2][1]=a[2][1];
        c[3][0]=p.subtract(a[3][0]).mod(p); c[3][1]=p.subtract(a[3][1]).mod(p);
        c[4][0]=p.subtract(a[4][0]).mod(p); c[4][1]=p.subtract(a[4][1]).mod(p);
        c[5][0]=p.subtract(a[5][0]).mod(p); c[5][1]=p.subtract(a[5][1]).mod(p);
        
        return c;
    }
    
    // opp Fp12
    public BigInteger[][] oppFp12( BigInteger[][] a){
        BigInteger[][] c = new BigInteger[6][2];
        for(int i=0;i<6;i++)for(int j=0;j<2;j++) c[i][j] = p.subtract(a[i][j]).mod(p);
        
        return c;
    }
    
    // Multiplication in Fp12
    public BigInteger[][] mulFp12( BigInteger[][] a, BigInteger[][] b){
        BigInteger[][] c = new BigInteger[6][2];
        BigInteger[][] c0 = new BigInteger[3][2]; BigInteger[][] c1 = new BigInteger[3][2];
        BigInteger[][] a0 = new BigInteger[3][2]; BigInteger[][] a1 = new BigInteger[3][2];
        BigInteger[][] b0 = new BigInteger[3][2]; BigInteger[][] b1 = new BigInteger[3][2];   
        BigInteger[][] t0 = new BigInteger[3][2]; BigInteger[][] t1 = new BigInteger[3][2];
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) a0[i][j]=a[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) a1[i][j]=a[i+3][j];   
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) b0[i][j]=b[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) b1[i][j]=b[i+3][j];
        
        t0 = mulFp6(a0,b0);
        t1 = mulFp6(a1,b1);
        c0 = addFp6(t0,mulByy(t1));
        c1 = subFp6(subFp6(mulFp6(addFp6(a0,a1),addFp6(b0,b1)),t0),t1);
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i][j]=c0[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i+3][j]=c1[i][j];
        //for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }
    
    // Multiplication by b=b0+b1w / b0 in Fp2 / b1= b10+ b11v+ 0v(2) 
    public BigInteger[][] mulFp12_b0b1( BigInteger[][] a, BigInteger[][] b ){
        BigInteger[][] c = new BigInteger[6][2];
        BigInteger[][] c0 = new BigInteger[3][2]; BigInteger[][] c1 = new BigInteger[3][2];
        BigInteger[][] a0 = new BigInteger[3][2]; BigInteger[][] a1 = new BigInteger[3][2];
        BigInteger[] b00 =new BigInteger[2]; BigInteger[] b10=new BigInteger[2]; BigInteger[] b11=new BigInteger[2];
        BigInteger[][] t0,t1,t2 = new BigInteger[3][2];
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) a0[i][j]=a[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) a1[i][j]=a[i+3][j];
        
        b00[0]=b[0][0]; b00[1]=b[0][1];
        b10[0]=b[3][0]; b10[1]=b[3][1];
        b11[0]=b[4][0]; b11[1]=b[4][1];
        
 
        t0 = mulFp6_b0(a0,b00); 
        t1 = mulFp6_b0b1(a1,b10,b11);   
        c0 = addFp6(t0,mulByy(t1));

        
        
        t2[0] = addFp2(b00,b10); t2[1] = b11; t2[2][0] = ZERO; t2[2][1] = ZERO; //
        c1 = mulFp6_b0b1(addFp6(a0,a1),t2[0],t2[1]);
        c1 = subFp6(subFp6(c1,t0),t1); 
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i][j]=c0[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i+3][j]=c1[i][j];
        //for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }
    
    // Squaring in Fp12
    public BigInteger[][] squarFp12( BigInteger[][] a){
        BigInteger[][] c = new BigInteger[6][2];
        BigInteger[][] c0,c1,c2,c3 = new BigInteger[3][2];
        BigInteger[][] a0 = new BigInteger[3][2]; BigInteger[][] a1 = new BigInteger[3][2];
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) a0[i][j]=a[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) a1[i][j]=a[i+3][j];
        
        c0 = subFp6(a0,a1);
        c3 = subFp6(a0,mulByy(a1));
        c2 = mulFp6(a0,a1);
        c0 = addFp6(mulFp6(c0,c3),c2);
        c1 = addFp6(c2,c2); // c1<-2c2
        c2 = mulByy(c2);
        c0 = addFp6(c0,c2);
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i][j]=c0[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i+3][j]=c1[i][j];
        
        //for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(c[i][j].toString(16));
        //System.out.println();
        return c;
    }

    
    //Squaring in Fp12 Version 2 Algorithme 24
    public BigInteger[][] squarFp12_V200( BigInteger[][] f){
        BigInteger[][] c = new BigInteger[6][2];
        BigInteger[][] c0 = null, c1 = new BigInteger[3][2];
        BigInteger[][] g = null, h  = new BigInteger[3][2];
        
        BigInteger[][] A, B, C = new BigInteger[2][2];
        BigInteger[][] tmp = new BigInteger[2][2];
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) g[i][j]=f[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) h[i][j]=f[i+3][j];
        
        /*g[0][0]=f[0][0] ; g[0][1]=f[0][1];
        g[1][0]=f[4][0] ; g[1][1]=f[4][1];
        g[2][0]=f[3][0] ; g[2][1]=f[3][1];
        
        h[0][0]=f[2][0] ; h[0][1]=f[2][1];
        h[1][0]=f[1][0] ; h[1][1]=f[1][1];
        h[2][0]=f[5][0] ; h[2][1]=f[5][1];*/
        
        tmp[0][0] = g[0][0];tmp[0][1] = g[0][1];
        tmp[1][0] = h[1][0];tmp[1][1] = h[1][1];  
        A = squarFp4(tmp);
        
        
        tmp[0][0] = h[0][0];tmp[0][1] = h[0][1];
        tmp[1][0] = g[2][0];tmp[1][1] = g[2][1];
        B = squarFp4(tmp);
 
        
        tmp[0][0] = g[1][0];tmp[0][1] = g[1][1];
        tmp[1][0] = h[2][0];tmp[1][1] = h[2][1];
        C = squarFp4(tmp);
        C[1] = mulByu(C[1]);
        
        
        c0[0] = subFp2(mulCFp2(A[0],THREE),mulCFp2(g[0],TWO)); 
        c0[1] = subFp2(mulCFp2(B[1],THREE),mulCFp2(g[1],TWO));
        c0[2] = subFp2(mulCFp2(C[0],THREE),mulCFp2(g[2],TWO));
        
        c1[0] = addFp2(mulCFp2(h[0],TWO),mulCFp2(C[1],THREE));
        c1[1] = addFp2(mulCFp2(h[1],TWO),mulCFp2(A[1],THREE));
        c1[2] = addFp2(mulCFp2(h[2],TWO),mulCFp2(B[0],THREE));
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i][j]=c0[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i+3][j]=c1[i][j];
        
        //for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(c[i][j].toString(16));
        //System.out.println();
        return c;
    }
   
    
        //Squaring in Fp12 Version 2 Algorithme 24
    public BigInteger[][] squarFp12_V2( BigInteger[][] f){
        BigInteger[][] c = new BigInteger[6][2];
        BigInteger[] c0, c1, c2, c3, c4, c5 = new BigInteger[2];
        BigInteger[] v0, v1, v2 = new BigInteger[2];
        BigInteger[] A0, A1, B0, B1, C0, C1 = new BigInteger[2];
        BigInteger[] u0, u1, u2 = new BigInteger[2];
        

        v0=karatsuba2(f[0],f[3],2);
        v1=karatsuba2(f[1],f[4],2);
        v2=karatsuba2(f[2],f[5],2);
        
        
        A0=addFp2(f[0],f[3]);
        A1=addFp2(f[0],mulByu(f[3]));

        B0=addFp2(f[1],f[4]);
        B1=addFp2(f[1],mulByu(f[4]));
        
        C0=addFp2(f[2],f[5]);
        C1=addFp2(f[2],mulByu(f[5]));
        
        u0=karatsuba2(A0,A1,2);
        u1=karatsuba2(B0,B1,2);
        u2=karatsuba2(C0,C1,2);
        
        A0=addFp2(v0,mulByu(v0));
        B0=addFp2(v1,mulByu(v1));
        C0=addFp2(v2,mulByu(v2));
        
        c0=subFp2(mulCFp2(subFp2(u0,A0),THREE),mulCFp2(f[0],TWO));
        c1=addFp2(mulCFp2(v2,SIX),mulCFp2(f[1],TWO));
        c2=subFp2(mulCFp2(subFp2(u1,B0),THREE),mulCFp2(f[2],TWO));
        
        c3=addFp2(mulCFp2(v0,SIX),mulCFp2(f[3],TWO));
        c4=subFp2(mulCFp2(subFp2(u2,C0),THREE),mulCFp2(f[4],TWO));
        c5=addFp2(mulCFp2(v1,SIX),mulCFp2(f[5],TWO));
        
        c[0]=c0; c[1]=c3;
        c[2]=c1; c[3]=c4;
        c[4]=c2; c[5]=c5;
        
        //for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(c[i][j].toString(16));
        //System.out.println();

        return c;
    }
    
    
    
    //Inverse in Fp12 
    public BigInteger[][] invFp12( BigInteger[][] a){
        BigInteger[][] c = new BigInteger[6][2];
        BigInteger[][] c0,c1 = new BigInteger[3][2];
        BigInteger[][] a0 = new BigInteger[3][2]; BigInteger[][] a1 = new BigInteger[3][2];
        BigInteger[][] t0,t1 = new BigInteger[3][2];
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) a0[i][j]=a[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) a1[i][j]=a[i+3][j];
        
        t0 = squarFp6(a0);
        t1 = squarFp6(a1);
        t0 = subFp6(t0,mulByy(t1));
        t1 = invFp6(t0);
        c0 = mulFp6(a0,t1);
        c1 = oppFp6(mulFp6(a1,t1));
        
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i][j]=c0[i][j];
        for(int i=0;i<3;i++)for(int j=0;j<2;j++) c[i+3][j]=c1[i][j];
        //for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }
    
    // Exponenetiation in Fp12
    public BigInteger[][] expFp12( BigInteger[][] a, BigInteger e){
        BigInteger[][] c = new BigInteger[6][2];
        
        for(int i=0;i<6;i++)for(int j=0;j<2;j++) c[i][j]=a[i][j]; // C<-A
        
        for(int i = 61; i>=0; i--){
            //c = squarFp12(c);  
            c = squarFp12_V2(c); //{algo 24}
            if( i==44 ) 
                c = mulFp12(c,a);
            if( i==54 ) 
                c = mulFp12(c,conjugate_Fp12(a));   
        }

        //for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }


    
    

    
    // Doublement + tangent 
    public BigInteger[][] doublingAndTangent( BigInteger[][] q, BigInteger[] p){
        BigInteger[][] pointT = new BigInteger[3][2]; //point T
        BigInteger[][] tmp = new BigInteger[7][2];
        BigInteger[][] l = new BigInteger[6][2];
        
        tmp[0] = karatsuba2( q[0], q[0], 2); //
        tmp[1] = karatsuba2( q[1], q[1], 2); //
        tmp[2] = karatsuba2( tmp[1], tmp[1], 2); //
        tmp[3] = subFp2(subFp2(karatsuba2(addFp2(tmp[1],q[0]),addFp2(tmp[1],q[0]),2),tmp[0]),tmp[2]); //
        tmp[3] = mulCFp2( tmp[3], TWO); //
        tmp[4] = mulCFp2( tmp[0], THREE); //
        
        tmp[6] = addFp2( q[0], tmp[4]); //
        tmp[5] = karatsuba2( tmp[4], tmp[4], 2); //
        
        pointT[0] = subFp2( tmp[5], mulCFp2( tmp[3], TWO)); //
        pointT[2] = subFp2(subFp2(karatsuba2(addFp2(q[1],q[2]),addFp2(q[1],q[2]),2),tmp[1]),karatsuba2(q[2],q[2],2)); //
        pointT[1] = subFp2(karatsuba2(subFp2(tmp[3],pointT[0]),tmp[4],2),mulCFp2(tmp[2],EIGHT)); //
        
        //System.out.println(pointT[0][0].toString(16));System.out.println(pointT[0][1].toString(16));
        //System.out.println(pointT[1][0].toString(16));System.out.println(pointT[1][1].toString(16));
        //System.out.println(pointT[2][0].toString(16));System.out.println(pointT[2][1].toString(16));
        
        //System.out.println();
        
        tmp[3] = mulCFp2(karatsuba2(tmp[4],karatsuba2(q[2],q[2],2),2),NTWO); //
        tmp[3] = mulCFp2(tmp[3],p[0]); //
        tmp[6] = subFp2(subFp2(subFp2(karatsuba2(tmp[6],tmp[6],2),tmp[0]),tmp[5]),mulCFp2(tmp[1],FOUR)); //
        tmp[0] = mulCFp2(karatsuba2(pointT[2],karatsuba2(q[2],q[2],2),2),TWO); //
        tmp[0] = mulCFp2(tmp[0],p[1]); //
        
        l[0][0] = tmp[0][0]; l[0][1] = tmp[0][1];
        l[1][0] = ZERO;      l[1][1] = ZERO;
        l[2][0] = ZERO;      l[2][1] = ZERO;
        l[3][0] = tmp[3][0]; l[3][1] = tmp[3][1];
        l[4][0] = tmp[6][0]; l[4][1] = tmp[6][1];
        l[5][0] = ZERO;      l[5][1] = ZERO;

        //for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(l[i][j].toString(16));
        //System.out.println();
        return l;
    }
    
    // Doublement 
    public BigInteger[][] doubling( BigInteger[][] q ){
        BigInteger[][] pointT = new BigInteger[3][2]; //point T
        BigInteger[][] tmp = new BigInteger[7][2];
        
        tmp[0] = karatsuba2( q[0], q[0], 2); //
        tmp[1] = karatsuba2( q[1], q[1], 2); //
        tmp[2] = karatsuba2( tmp[1], tmp[1], 2); //
        tmp[3] = subFp2(subFp2(karatsuba2(addFp2(tmp[1],q[0]),addFp2(tmp[1],q[0]),2),tmp[0]),tmp[2]); //
        tmp[3] = mulCFp2( tmp[3], TWO); //
        tmp[4] = mulCFp2( tmp[0], THREE); //
        
        tmp[6] = addFp2( q[0], tmp[4]); //
        tmp[5] = karatsuba2( tmp[4], tmp[4], 2); //
        
        pointT[0] = subFp2( tmp[5], mulCFp2( tmp[3], TWO)); //
        pointT[2] = subFp2(subFp2(karatsuba2(addFp2(q[1],q[2]),addFp2(q[1],q[2]),2),tmp[1]),karatsuba2(q[2],q[2],2)); //
        pointT[1] = subFp2(karatsuba2(subFp2(tmp[3],pointT[0]),tmp[4],2),mulCFp2(tmp[2],EIGHT)); //
        
        //System.out.println(pointT[0][0].toString(16));System.out.println(pointT[0][1].toString(16));
        //System.out.println(pointT[1][0].toString(16));System.out.println(pointT[1][1].toString(16));
        //System.out.println(pointT[2][0].toString(16));System.out.println(pointT[2][1].toString(16));

        return pointT;
    }
    
    // Addition + Line
    public BigInteger[][] additionAndLine( BigInteger[][] q, BigInteger[][] r, BigInteger[] p){
        BigInteger[][] pointT = new BigInteger[3][2]; //point T
        BigInteger[][] tmp = new BigInteger[11][2];
        BigInteger[][] l = new BigInteger[6][2];
        
        tmp[0]= karatsuba2(q[0],karatsuba2(r[2],r[2],2),2); //
        tmp[1]= subFp2(subFp2(karatsuba2(addFp2(q[1],r[2]),addFp2(q[1],r[2]),2),karatsuba2(q[1],q[1],2)),karatsuba2(r[2],r[2],2)); //
        tmp[1]= karatsuba2(tmp[1],karatsuba2(r[2],r[2],2),2); //
        tmp[2]= subFp2(tmp[0],r[0]); //
        tmp[3]= karatsuba2(tmp[2],tmp[2],2); //
        tmp[4]= mulCFp2(tmp[3],FOUR); //
        tmp[5]= karatsuba2(tmp[4],tmp[2],2); //
        tmp[6]= subFp2(tmp[1],mulCFp2(r[1],TWO)); //
        tmp[9]= karatsuba2(tmp[6],q[0],2); //
        tmp[7]= karatsuba2(r[0],tmp[4],2); //
        
        pointT[0]= subFp2(subFp2(karatsuba2(tmp[6],tmp[6],2),tmp[5]),mulCFp2(tmp[7],TWO)); //
        pointT[2]= subFp2(subFp2(karatsuba2(addFp2(r[2],tmp[2]),addFp2(r[2],tmp[2]),2),karatsuba2(r[2],r[2],2)),tmp[3]); //
        
        tmp[10]= addFp2(q[1],pointT[2]);//
        tmp[8]= karatsuba2(subFp2(tmp[7],pointT[0]),tmp[6],2); //
        tmp[0]= mulCFp2(karatsuba2(r[1],tmp[5],2),TWO); //
        
        pointT[1]= subFp2(tmp[8],tmp[0]); //
        
        //System.out.println(pointT[0][0].toString(16));System.out.println(pointT[0][1].toString(16));
        //System.out.println(pointT[1][0].toString(16));System.out.println(pointT[1][1].toString(16));
        //System.out.println(pointT[2][0].toString(16));System.out.println(pointT[2][1].toString(16));
        //System.out.println();
        
        tmp[10] = subFp2(subFp2(karatsuba2(tmp[10],tmp[10],2),karatsuba2(q[1],q[1],2)),karatsuba2(pointT[2],pointT[2],2)); //
        tmp[9] = subFp2(mulCFp2(tmp[9],TWO),tmp[10]); //
        tmp[10] = mulCFp2(mulCFp2(pointT[2],p[1]),TWO); //
        tmp[6] = mulCFp2(tmp[6],NONE); //
        tmp[1] = mulCFp2(mulCFp2(tmp[6],p[0]),TWO); //
        
        l[0][0] = tmp[10][0]; l[0][1] = tmp[10][1];
        l[1][0] = ZERO;       l[1][1] = ZERO;
        l[2][0] = ZERO;       l[2][1] = ZERO;
        l[3][0] = tmp[1][0];  l[3][1] = tmp[1][1];
        l[4][0] = tmp[9][0];  l[4][1] = tmp[9][1];
        l[5][0] = ZERO;       l[5][1] = ZERO;
        
        //for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(l[i][j].toString(16));
        //System.out.println();
        return l;
    }
    
    // Addition
    public BigInteger[][] addition ( BigInteger[][] q, BigInteger[][] r ){
        BigInteger[][] pointT = new BigInteger[3][2]; //point T
        BigInteger[][] tmp = new BigInteger[11][2];
        
        tmp[0]= karatsuba2(q[0],karatsuba2(r[2],r[2],2),2); //
        tmp[1]= subFp2(subFp2(karatsuba2(addFp2(q[1],r[2]),addFp2(q[1],r[2]),2),karatsuba2(q[1],q[1],2)),karatsuba2(r[2],r[2],2)); //
        tmp[1]= karatsuba2(tmp[1],karatsuba2(r[2],r[2],2),2); //
        tmp[2]= subFp2(tmp[0],r[0]); //
        tmp[3]= karatsuba2(tmp[2],tmp[2],2); //
        tmp[4]= mulCFp2(tmp[3],FOUR); //
        tmp[5]= karatsuba2(tmp[4],tmp[2],2); //
        tmp[6]= subFp2(tmp[1],mulCFp2(r[1],TWO)); //
        tmp[9]= karatsuba2(tmp[6],q[0],2); //
        tmp[7]= karatsuba2(r[0],tmp[4],2); //
        
        pointT[0]= subFp2(subFp2(karatsuba2(tmp[6],tmp[6],2),tmp[5]),mulCFp2(tmp[7],TWO)); //
        pointT[2]= subFp2(subFp2(karatsuba2(addFp2(r[2],tmp[2]),addFp2(r[2],tmp[2]),2),karatsuba2(r[2],r[2],2)),tmp[3]); //
        
        tmp[10]= addFp2(q[1],pointT[2]);//
        tmp[8]= karatsuba2(subFp2(tmp[7],pointT[0]),tmp[6],2); //
        tmp[0]= mulCFp2(karatsuba2(r[1],tmp[5],2),TWO); //
        
        pointT[1]= subFp2(tmp[8],tmp[0]); //
        
        //System.out.println(pointT[0][0].toString(16));System.out.println(pointT[0][1].toString(16));
        //System.out.println(pointT[1][0].toString(16));System.out.println(pointT[1][1].toString(16));
        //System.out.println(pointT[2][0].toString(16));System.out.println(pointT[2][1].toString(16));

        return pointT;
    }
    
    // Frobenius raised to p of f dans Fp12
    public BigInteger[][] frobenius_p( BigInteger[][] f ) {
        BigInteger[][] c = new BigInteger[6][2];
        BigInteger[] t1 = new BigInteger[2]; BigInteger[] t2 = new BigInteger[2];
        BigInteger[] t3 = new BigInteger[2]; BigInteger[] t4 = new BigInteger[2];
        BigInteger[] t5 = new BigInteger[2]; BigInteger[] t6 = new BigInteger[2];

        t1 = conjugate(f[0]); t2 = conjugate(f[3]);
        t3 = conjugate(f[1]); t4 = conjugate(f[4]);
        t5 = conjugate(f[2]); t6 = conjugate(f[5]);
        
        t2 = mulCFp2(t2,y11);
        t3 = mulCFp2(t3,y12);
        t4 = mulCFp2(t4,y13);
        t5 = mulCFp2(t5,y14);
        t6 = mulCFp2(t6,y15);
     
        c[0][0] = t1[0]; c[0][1] = t1[1];
        c[1][0] = t3[0]; c[1][1] = t3[1];
        c[2][0] = t5[0]; c[2][1] = t5[1];
        c[3][0] = t2[0]; c[3][1] = t2[1];
        c[4][0] = t4[0]; c[4][1] = t4[1];
        c[5][0] = t6[0]; c[5][1] = t6[1];
        
        //for(int i=0;i<6;i++) for(int j=0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }
    
    // Frobenius raised to p(2) of f dans Fp12
    public BigInteger[][] frobenius_p2( BigInteger[][] f ) {
        BigInteger[][] c = new BigInteger[6][2];
        BigInteger[] t1 = new BigInteger[2]; BigInteger[] t2 = new BigInteger[2];
        BigInteger[] t3 = new BigInteger[2]; BigInteger[] t4 = new BigInteger[2];
        BigInteger[] t5 = new BigInteger[2]; BigInteger[] t6 = new BigInteger[2];

        t1 = f[0];
        t2 = mulCFp2(f[3],y21);
        t3 = mulCFp2(f[1],y22);
        t4 = mulCFp2(f[4],y23);
        t5 = mulCFp2(f[2],y24);
        t6 = mulCFp2(f[5],y25);
        
        c[0][0] = t1[0]; c[0][1] = t1[1];
        c[1][0] = t3[0]; c[1][1] = t3[1];
        c[2][0] = t5[0]; c[2][1] = t5[1];
        c[3][0] = t2[0]; c[3][1] = t2[1];
        c[4][0] = t4[0]; c[4][1] = t4[1];
        c[5][0] = t6[0]; c[5][1] = t6[1];
        
        //for(int i=0;i<6;i++) for(int j=0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }
    
    // Frobenius raised to p(3) of f dans Fp12
    public BigInteger[][] frobenius_p3( BigInteger[][] f ) {
        BigInteger[][] c = new BigInteger[6][2];
        BigInteger[] t1 = new BigInteger[2]; BigInteger[] t2 = new BigInteger[2];
        BigInteger[] t3 = new BigInteger[2]; BigInteger[] t4 = new BigInteger[2];
        BigInteger[] t5 = new BigInteger[2]; BigInteger[] t6 = new BigInteger[2];

        t1 = conjugate(f[0]); t2 = conjugate(f[3]);
        t3 = conjugate(f[1]); t4 = conjugate(f[4]);
        t5 = conjugate(f[2]); t6 = conjugate(f[5]);
        
        t2 = mulCFp2(t2,y31);
        t3 = mulCFp2(t3,y32);
        t4 = mulCFp2(t4,y33);
        t5 = mulCFp2(t5,y34);
        t6 = mulCFp2(t6,y35);
       
        c[0][0] = t1[0]; c[0][1] = t1[1];
        c[1][0] = t3[0]; c[1][1] = t3[1];
        c[2][0] = t5[0]; c[2][1] = t5[1];
        c[3][0] = t2[0]; c[3][1] = t2[1];
        c[4][0] = t4[0]; c[4][1] = t4[1];
        c[5][0] = t6[0]; c[5][1] = t6[1];
        
        //for(int i=0;i<6;i++) for(int j=0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }
    
    // Final Exponentiation
    public BigInteger[][] expFinal( BigInteger[][] f, BigInteger e){
        BigInteger[][] c = new BigInteger[6][2];
        BigInteger[][] f1 = new BigInteger[6][2],f2 = new BigInteger[6][2];
        BigInteger[][] ft1 = new BigInteger[6][2],ft2 = new BigInteger[6][2],ft3 = new BigInteger[6][2];
        BigInteger[][] fp1 = new BigInteger[6][2],fp2 = new BigInteger[6][2],fp3 = new BigInteger[6][2];
        BigInteger[][] y0 = new BigInteger[6][2],y1 = new BigInteger[6][2],y2 = new BigInteger[6][2],y3 = new BigInteger[6][2],
                       y4 = new BigInteger[6][2],y5 = new BigInteger[6][2],y6 = new BigInteger[6][2];
        BigInteger[][] t0 = new BigInteger[6][2],t1 = new BigInteger[6][2];
        
        f1 = conjugate_Fp12(f);
        f2 = invFp12(f);
        f = mulFp12(f1,f2);
        f = mulFp12(frobenius_p2(f),f);

        ft1 = expFp12(f,t);
        ft2 = expFp12(ft1,t);
        ft3 = expFp12(ft2,t);
        
        fp1 = frobenius_p(f);
        fp2 = frobenius_p2(f);
        fp3 = frobenius_p3(f);
        
        y0 = mulFp12(mulFp12(fp1,fp2),fp3);
        y1 = conjugate_Fp12(f);
        y2 = frobenius_p2(ft2);
        y3 = frobenius_p(ft1);
        y3 = conjugate_Fp12(y3);
        y4 = mulFp12(frobenius_p(ft2),ft1);
        y4 = conjugate_Fp12(y4);
        y5 = conjugate_Fp12(ft2);
        y6 = mulFp12(frobenius_p(ft3),ft3);
        y6 = conjugate_Fp12(y6);
        
        t0 = mulFp12(mulFp12(squarFp12_V2(y6),y4),y5);// squarFp12_V2
        t1 = mulFp12(mulFp12(y3,y5),t0);
        t0 = mulFp12(t0,y2);
        t1 = squarFp12_V2(mulFp12(squarFp12_V2(t1),t0));//
        t0 = mulFp12(t1,y1);
        t1 = mulFp12(t1,y0);
        t0 = squarFp12_V2(t0);//
        
        c = mulFp12(t1,t0);
        
        //for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(c[i][j].toString(16));
        return c;
    }
    


    // Ate Pairing 
    public BigInteger[][] AtePairing( BigInteger[][] Q, BigInteger[] P){
        BigInteger[][] f = new BigInteger[6][2];
        BigInteger[][] T = new BigInteger[3][2];
        for(int i=0;i<6;i++) for(int j=0;j<2;j++) f[i][j]=ZERO; f[0][0]=ONE;
        for(int i=0;i<3;i++) for(int j=0;j<2;j++) T[i][j] = Q[i][j];

        
        BigInteger tt = tr.subtract(ONE); //tr-1
        int lengthtt = tt.bitLength();
        byte[] binarraytt = new byte[lengthtt];
        for(int i=0;i<lengthtt;i++){
            binarraytt[i] = tt.mod(TWO).byteValue();
            tt = tt.divide(TWO);
        }
        
        for(int i = lengthtt-2; i>=0; i--){
            f = mulFp12(squarFp12(f),doublingAndTangent(T,P));
            T = doubling(T);
            if(binarraytt[i] == 1){ 
                f = mulFp12(f,additionAndLine(Q,T,P));
                T = addition(Q,T);
            }
        }

        //for(int i=0;i<6;i++)for(int j=0;j<2;j++) f[i][j]=f[i][j].multiply(InvR).mod(p);
        //for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(f[i][j].toString(16));
        //System.out.println();

        
        f = expFinal(f,exp);
        
        for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(f[i][j].toString(16));
        return f;
    }
    
    // Optimal Ate Pairing 
    public BigInteger[][] OptAtePairing( BigInteger[][] Q, BigInteger[] P){
        BigInteger[][] f = new BigInteger[6][2];
        BigInteger[][] T = new BigInteger[3][2];
        for(int i=0;i<6;i++) for(int j=0;j<2;j++) f[i][j]=ZERO; f[0][0]=ONE;
        for(int i=0;i<3;i++) for(int j=0;j<2;j++) T[i][j] = Q[i][j];

       
        BigInteger ss = s;
        int lengthss = ss.bitLength();
        byte[] binarrayss = new byte[lengthss];
        for(int i=0;i<lengthss;i++){
            binarrayss[i] = ss.mod(TWO).byteValue();
            ss = ss.divide(TWO);
        }
        
        for(int i = L-2; i>=0; i--){
            //f = mulFp12(squarFp12(f),doublingAndTangent(T,P));
            f = mulFp12_b0b1(squarFp12(f),doublingAndTangent(T,P));
            
            T = doubling(T);
            if(binarrayss[i] == 1){ 
                f = mulFp12_b0b1(f,additionAndLine(Q,T,P));
                T = addition(Q,T);}

        }
        
        //for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(f[i][j].toString(16));
        //System.out.println();
        //for(int i=0;i<3;i++)for(int j=0;j<2;j++) System.out.println(T[i][j].toString(16));
        //System.out.println();
        
        BigInteger[][] Q1 = new BigInteger[3][2]; 
        Q1[0] = conjugate(Q[0]);  //Q1 <- frob(Q)
        Q1[0] = mulCFp2 (Q1[0],y12);
        
        Q1[1] = conjugate(Q[1]);
        Q1[1] = mulCFp2 (Q1[1],y13);
        
        f = mulFp12_b0b1(f,additionAndLine(Q1,T,P));
        T = addition(Q1,T);
        
        BigInteger[][] Q2 = new BigInteger[3][2];
        Q2[0] = conjugate( Q1[0]);  //Q2 <- frob_2(Q) 
        Q2[0] = mulCFp2 (Q2[0],y12);
        
        Q2[1] = conjugate(Q1[1]);
        Q2[1] = mulCFp2(Q2[1],y13);
        
        Q2[1][0] = p.subtract(Q2[1][0]).mod(p); Q2[1][1] = p.subtract(Q2[1][1]).mod(p); // Q2 <- -Q2

        f = mulFp12_b0b1(f,additionAndLine(Q2,T,P));
        T = addition(Q2,T);
        
        f = expFinal(f,exp);
        
        for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(f[i][j].toString(16));
        System.out.println();

        return f;
    }
    
    // Optimal Ate Pairing 
    public BigInteger[][] OptAtePairing_ver1( BigInteger[][] Q, BigInteger[] P){
        BigInteger[][] f = new BigInteger[6][2];
        BigInteger[][] T = new BigInteger[3][2];
        BigInteger[][] opQ = new BigInteger[3][2];
        for(int i=0;i<6;i++) for(int j=0;j<2;j++) f[i][j]=ZERO; f[0][0]=ONE; // f <-1
        for(int i=0;i<3;i++) for(int j=0;j<2;j++) T[i][j] = Q[i][j]; //T <- Q
        opQ[0][0] = Q[0][0];                    opQ[0][1] = Q[0][1]; //opQ <- -Q
        opQ[1][0] = p.subtract(Q[1][0]).mod(p); opQ[1][1] = p.subtract(Q[1][1]).mod(p);
        opQ[2][0] = Q[2][0];                    opQ[2][1] = Q[2][1];
        
        //f = mulFp12(f,additionAndLine(Q,T,P));
        //T = addition(Q,T);
        
        for(int i = 63; i>=0; i--){
            f = mulFp12(squarFp12(f),doublingAndTangent(T,P));
            T = doubling(T);
            
            
            if( (i==63) || (i==46) || (i==45) || (i==1) ){ 
                f = mulFp12(f,additionAndLine(Q,T,P));
                T = addition(Q,T);
            }
            if( i==56 || i==55 ){ 
                f = mulFp12(f,additionAndLine(opQ,T,P));
                T = addition(opQ,T);
            }          
        }
        
        //for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(f[i][j].toString(16));
        //System.out.println();
        //for(int i=0;i<3;i++)for(int j=0;j<2;j++) System.out.println(T[i][j].toString(16));
        //System.out.println();
        
        BigInteger[][] Q1 = new BigInteger[3][2]; 
        Q1[0] = conjugate(Q[0]);  //Q1 <- frob(Q)
        Q1[0] = mulCFp2 (Q1[0],y12);
        
        Q1[1] = conjugate(Q[1]);
        Q1[1] = mulCFp2 (Q1[1],y13);
        
        f = mulFp12(f,additionAndLine(Q1,T,P));
        T = addition(Q1,T);
        
        BigInteger[][] Q2 = new BigInteger[3][2];
        Q2[0] = conjugate( Q1[0]);  //Q2 <- frob_2(Q) 
        Q2[0] = mulCFp2 (Q2[0],y12);
        
        Q2[1] = conjugate(Q1[1]);
        Q2[1] = mulCFp2(Q2[1],y13);
        
        Q2[1][0] = p.subtract(Q2[1][0]).mod(p); Q2[1][1] = p.subtract(Q2[1][1]).mod(p); // Q2 <- -Q2

        f = mulFp12(f,additionAndLine(Q2,T,P));
        T = addition(Q2,T);

        f = expFinal(f,exp);
        
        for(int i=0;i<6;i++)for(int j=0;j<2;j++) System.out.println(f[i][j].toString(16));
        return f;
    }
    
}
