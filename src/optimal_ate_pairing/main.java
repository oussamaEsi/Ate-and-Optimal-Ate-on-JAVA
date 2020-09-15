package optimal_ate_pairing;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;


public class main { 
    
    public static void main(String[] args) {

/*
        BigInteger[][] a = new BigInteger[6][2];
        a[0][0] = new BigInteger("1787ec561c559aef7d4a6a47b2de700b8425499f76a85a41b27617a6c74240f4",16);
        a[0][1] = new BigInteger("3f8b3f4c99f87fa5b1b6581a8c23bafc68249e1e4a869861020bead6dd05ab9",16);
        a[1][0] = new BigInteger("9ccf2b642f10a5a4b0574709bdbfed2b9d2555a00b7de3162ca4f343e08cd66",16);
        a[1][1] = new BigInteger("1786b8cb262e12ba84976f58a7a14591466a64cbc19562ab946632266bfe3f99",16);
        a[2][0] = new BigInteger("19ec86103fcc9f580b88fa264f51ff12cba7de8dbec4f266ea0d90ed6f2eb591",16);
	a[2][1] = new BigInteger("17ab0274246f3d53023689e2d952946555022069e6a83d00a58f39fd204d702",16);
        
	a[3][0] = new BigInteger("20a99bfbc3171f89e5159a7e24d6ab2756225b13f6ccbdf27bcad0cbbb026f2d",16);
	a[3][1] = new BigInteger("e9087c37786559fb5ba3d35b0b75dfaa09e23f7f65caf44c8e73436ba691ffa",16);
	a[4][0] = new BigInteger("776c2404e53db19702068e716322ffd1047f9946586ce029d6bb9023b895762",16);
	a[4][1] = new BigInteger("c4e3f5425815a573d69f644c2e95f76f72603c05c2400d7eb2675ba76dd1070",16);
	a[5][0] = new BigInteger("218cc71be3ed68ad17d49043cf9801d7e176566d6a8d0b335bca9944d63040c8",16);
	a[5][1] = new BigInteger("10ead7eac6c343b416cf00593b0e08b04a6d65123cd97a0f2406335333b7d2f1",16);
        
        BigInteger[] b0 = new BigInteger[2];
        b0[0] = new BigInteger("20a99bfbc3171f89e5159a7e24d6ab2756225b13f6ccbdf27bcad0cbbb026f2d",16);
        b0[1] = new BigInteger("e9087c37786559fb5ba3d35b0b75dfaa09e23f7f65caf44c8e73436ba691ffa",16);
        
        BigInteger[] b1 = new BigInteger[2];
        b1[0] = new BigInteger("776c2404e53db19702068e716322ffd1047f9946586ce029d6bb9023b895762",16);
        b1[1] = new BigInteger("c4e3f5425815a573d69f644c2e95f76f72603c05c2400d7eb2675ba76dd1070",16);


        
        Outils.affichierToTabBigInteger32Bit("f.w0.v0[0]",a[0][0]);
        Outils.affichierToTabBigInteger32Bit("f.w0.v0[1]",a[0][1]);
        Outils.affichierToTabBigInteger32Bit("f.w0.v1[0]",a[1][0]);
        Outils.affichierToTabBigInteger32Bit("f.w0.v1[1]",a[1][1]);
        Outils.affichierToTabBigInteger32Bit("f.w0.v2[0]",a[2][0]);
        Outils.affichierToTabBigInteger32Bit("f.w0.v2[1]",a[2][1]);

        Outils.affichierToTabBigInteger32Bit("f.w1.v0[0]",a[3][0]);
        Outils.affichierToTabBigInteger32Bit("f.w1.v0[1]",a[3][1]);
        Outils.affichierToTabBigInteger32Bit("f.w1.v1[0]",a[4][0]);
        Outils.affichierToTabBigInteger32Bit("f.w1.v1[1]",a[4][1]);
        Outils.affichierToTabBigInteger32Bit("f.w1.v2[0]",a[5][0]);
        Outils.affichierToTabBigInteger32Bit("f.w1.v2[1]",a[5][1]);
*/
        
      // Optimat Ate Pairing 
        BigInteger[][] Q = new BigInteger[3][2];
        Q[0][0] = new BigInteger("19B0BEA4AFE4C330DA93CC3533DA38A9F430B471C6F8A536E81962ED967909B5",16);
        Q[0][1] = new BigInteger("A1CF585585A61C6E9880B1F2A5C539F7D906FFF238FA6341E1DE1A2E45C3F72",16);
        Q[1][0] = new BigInteger("17ABD366EBBD65333E49C711A80A0CF6D24ADF1B9B3990EEDCC91731384D2627",16);
        Q[1][1] = new BigInteger("EE97D6DE9902A27D00E952232A78700863BC9AA9BE960C32F5BF9FD0A32D345",16);
        Q[2][0] = new BigInteger("1",16);
        Q[2][1] = new BigInteger("0",16);
        
        BigInteger[] P = new BigInteger[2];
        P[0] = new BigInteger("1",16);
        P[1] = new BigInteger("d45589b158faaf6ab0e4ad38d998e9982e7ff63964ee1460342a592677cccb0",16); //La racine de 6

        
        Operations_Fpk op_Fpk = new Operations_Fpk();
        op_Fpk.OptAtePairing(Q,P);

        System.out.println();
        op_Fpk.OptAtePairing_ver1(Q,P);

        System.out.println();
        op_Fpk.AtePairing(Q,P); 


/*
        // Karatsuba -- VHDL
        BigInteger[][] Q = new BigInteger[3][2];
        Q[0][0] = new BigInteger("19B0BEA4AFE4C330DA93CC3533DA38A9F430B471C6F8A536E81962ED967909B5",16);
        Q[0][1] = new BigInteger("A1CF585585A61C6E9880B1F2A5C539F7D906FFF238FA6341E1DE1A2E45C3F72",16);
        Q[1][0] = new BigInteger("17ABD366EBBD65333E49C711A80A0CF6D24ADF1B9B3990EEDCC91731384D2627",16);
        Q[1][1] = new BigInteger("EE97D6DE9902A27D00E952232A78700863BC9AA9BE960C32F5BF9FD0A32D345",16);
        Q[2][0] = new BigInteger("1",16);
        Q[2][1] = new BigInteger("0",16);
        
        BigInteger[] P = new BigInteger[2];
        P[0] = new BigInteger("1",16);
        P[1] = new BigInteger("d45589b158faaf6ab0e4ad38d998e9982e7ff63964ee1460342a592677cccb0",16); //La racine de 6
        
        
        Operations_Fpk op_Fpk = new Operations_Fpk();
        
        //Outils.affichierToTabBigInteger32Bit("p",op_Fpk.p);
        //System.out.println();
        //Outils.affichierToTabBigInteger32Bit("a0",Q[0][0]);
        //System.out.println();
        //Outils.affichierToTabBigInteger32Bit("a1",Q[0][1]);
        //System.out.println();
        //Outils.affichierToTabBigInteger32Bit("b0",Q[1][0]);
        //System.out.println();
        //Outils.affichierToTabBigInteger32Bit("b1",Q[1][1]);
        //System.out.println();*/
   
        //BigInteger a00 = Q[0][0].multiply(Q[1][0]).multiply(op_Fpk.InvR).mod(op_Fpk.p);
        //Outils.affichierToTabBigInteger32Bit("a00",a00);
        //System.out.println();
        
        //BigInteger a0 = (Q[0][0].add(Q[0][1])).multiply(Q[1][0]).multiply(op_Fpk.InvR).mod(op_Fpk.p);
        //Outils.affichierToTabBigInteger32Bit("a0",a0);
        //System.out.println();
      
        //BigInteger T = a00.subtract(a0).mod(op_Fpk.p);
        //Outils.affichierToTabBigInteger32Bit("T",T.multiply(op_Fpk.InvR).mod(op_Fpk.p));
        //System.out.println();
        
        //BigInteger Res = T.subtract(Q[0][1]).mod(op_Fpk.p);
        //Outils.affichierToTabBigInteger32Bit("Res",Res);
        //System.out.println();
        
        //op_Fpk.karatsuba2(Q[0],Q[1],2);
      
        //BigInteger T1 = Q[0][1].multiply(Q[1][1]).mod(op_Fpk.p).multiply(op_Fpk.InvR).mod(op_Fpk.p);
        //Outils.affichierToTabBigInteger32Bit("T1",T1);
        //System.out.println(); 

/*        
        BigInteger AS = Q[0][0].add(Q[0][1]).mod(op_Fpk.p);
        Outils.affichierToTabBigInteger32Bit("AS",AS);
        System.out.println();        

        BigInteger T = Q[0][0].multiply(Q[1][0]).multiply(op_Fpk.InvR).mod(op_Fpk.p);
        Outils.affichierToTabBigInteger32Bit("T",T);
        System.out.println();
        
        BigInteger T1 = Q[0][1].multiply(Q[1][1]).multiply(op_Fpk.InvR).mod(op_Fpk.p);
        Outils.affichierToTabBigInteger32Bit("T1",T1);
        System.out.println();
        
        BigInteger AS1 = Q[1][0].add(Q[1][1]).mod(op_Fpk.p);
        Outils.affichierToTabBigInteger32Bit("AS1",AS1);
        System.out.println();

        System.out.println("    ----     --- ---     ---- ");
        
        T = AS.multiply(AS1).multiply(op_Fpk.InvR).mod(op_Fpk.p);
        Outils.affichierToTabBigInteger32Bit("T",T);
        System.out.println(); 
        
        T1 = T1.multiply(op_Fpk.u).multiply(op_Fpk.InvR).mod(op_Fpk.p);
        Outils.affichierToTabBigInteger32Bit("T1",T1);
        System.out.println();
*/
        
        /*Outils.affichierToTabBigInteger32Bit("f.w0.v0[0]",tmp[0][0]);
        Outils.affichierToTabBigInteger32Bit("f.w0.v0[1]",tmp[0][1]);
        Outils.affichierToTabBigInteger32Bit("f.w0.v1[0]",tmp[1][0]);
        Outils.affichierToTabBigInteger32Bit("f.w0.v1[1]",tmp[1][1]);
        Outils.affichierToTabBigInteger32Bit("f.w0.v2[0]",tmp[2][0]);
        Outils.affichierToTabBigInteger32Bit("f.w0.v2[1]",tmp[2][1]);

        Outils.affichierToTabBigInteger32Bit("f.w1.v0[0]",tmp[3][0]);
        Outils.affichierToTabBigInteger32Bit("f.w1.v0[1]",tmp[3][1]);
        Outils.affichierToTabBigInteger32Bit("f.w1.v1[0]",tmp[4][0]);
        Outils.affichierToTabBigInteger32Bit("f.w1.v1[1]",tmp[4][1]);
        Outils.affichierToTabBigInteger32Bit("f.w1.v2[0]",tmp[5][0]);
        Outils.affichierToTabBigInteger32Bit("f.w1.v2[1]",tmp[5][1]);*/
        
        
        //System.out.println(op_Fpk.t.toString(2));System.out.println(op_Fpk.t.bitLength());
        //System.out.println(op_Fpk.t2.toString(2));System.out.println(op_Fpk.t2.bitLength());
        //System.out.println(op_Fpk.t3.toString(2));System.out.println(op_Fpk.t3.bitLength());
        
/*  //verifier l'expo        
        BigInteger ZERO    = new BigInteger("0");
        BigInteger ONE     = new BigInteger("1");
        BigInteger TWO     = new BigInteger("2");
    
        BigInteger p = new BigInteger("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF",16);
        int Taille_p = 256;
        BigInteger base = TWO.pow(32);          
        BigInteger R = (TWO.pow(Taille_p+32)).mod(p); // R=B(n) / B: base / n: nmbr de paquets
        BigInteger R2 = R.pow(2).mod(p);
        BigInteger InvR = R.modInverse(p);
        BigInteger Np = (p.negate()).modInverse(base); // N'=(-p)(-1)mod(base)
        
        BigInteger e = new BigInteger("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFD",16);
        BigInteger a = new BigInteger("A59A3BB6C46DD1C1E3906C42BC0AFB6A635A623797543294D2F878CA16B46099",16);

        BigInteger res= a.modPow(e, p);

        Outils.affichierToTabBigInteger32Bit("res", res);
*/



        
        /*System.out.println(op_Fpk.y21.toString(16));
        Outils.affichierToTabBigInteger32Bit("y21",op_Fpk.y21);
        System.out.println(op_Fpk.y22.toString(16));
        Outils.affichierToTabBigInteger32Bit("y22",op_Fpk.y22);
        System.out.println(op_Fpk.y23.toString(16));
        Outils.affichierToTabBigInteger32Bit("y23",op_Fpk.y23);
        System.out.println(op_Fpk.y24.toString(16));
        Outils.affichierToTabBigInteger32Bit("y24",op_Fpk.y24);
        System.out.println(op_Fpk.y25.toString(16));
        Outils.affichierToTabBigInteger32Bit("y25",op_Fpk.y25);*/
        
        
/*       
        BigInteger[][] a = new BigInteger[6][2];
        a[0][0] = new BigInteger("1787ec561c559aef7d4a6a47b2de700b8425499f76a85a41b27617a6c74240f4",16);
        a[0][1] = new BigInteger("3f8b3f4c99f87fa5b1b6581a8c23bafc68249e1e4a869861020bead6dd05ab9",16);
        a[1][0] = new BigInteger("9ccf2b642f10a5a4b0574709bdbfed2b9d2555a00b7de3162ca4f343e08cd66",16);
        a[1][1] = new BigInteger("1786b8cb262e12ba84976f58a7a14591466a64cbc19562ab946632266bfe3f99",16);
        a[2][0] = new BigInteger("19ec86103fcc9f580b88fa264f51ff12cba7de8dbec4f266ea0d90ed6f2eb591",16);
	a[2][1] = new BigInteger("17ab0274246f3d53023689e2d952946555022069e6a83d00a58f39fd204d702",16);
        
	a[3][0] = new BigInteger("20a99bfbc3171f89e5159a7e24d6ab2756225b13f6ccbdf27bcad0cbbb026f2d",16);
	a[3][1] = new BigInteger("e9087c37786559fb5ba3d35b0b75dfaa09e23f7f65caf44c8e73436ba691ffa",16);
	a[4][0] = new BigInteger("776c2404e53db19702068e716322ffd1047f9946586ce029d6bb9023b895762",16);
	a[4][1] = new BigInteger("c4e3f5425815a573d69f644c2e95f76f72603c05c2400d7eb2675ba76dd1070",16);
	a[5][0] = new BigInteger("218cc71be3ed68ad17d49043cf9801d7e176566d6a8d0b335bca9944d63040c8",16);
	a[5][1] = new BigInteger("10ead7eac6c343b416cf00593b0e08b04a6d65123cd97a0f2406335333b7d2f1",16);

        BigInteger[][] b = new BigInteger[6][2];
        b[0][0] = new BigInteger("42b372caea10d589d7706104375146e516c81e0635a81de07d68681bbcf3e47",16);
        b[0][1] = new BigInteger("1764493a1277fb89bdfd9bff3a1cb9b4030ddf450c939ddc6c1d3bdd656cad66",16);
        b[1][0] = new BigInteger("1debae11b1fd080ba5f8442cfe5fc30576763fc622a0d3e21418d141d0a4d913",16);
        b[1][1] = new BigInteger("60678890b52d64d69fa00bdd617a6fb5b599beb2b14d4587c3bab640dd05731",16);
        b[2][0] = new BigInteger("197873bad41eb318c84b1bc3bb24898bd6fd2889eb591121ed66b68cd3718353",16);
	b[2][1] = new BigInteger("1cb1c93f9fefc51cd9a31e774b8f2e03f2aeceacca73323fc1bdfb952b3c175b",16);
        
	b[3][0] = new BigInteger("62435e96485042f36d0dc5b9f9779963116b07dea3e69ba4855e8b7c8f7cfb6",16);
	b[3][1] = new BigInteger("120eb27c11c40e852494384017ddda632082f1709843113d91e1b79f26697fd3",16);
	b[4][0] = new BigInteger("2171d53ddaccd4aa98d9c2279d5e1781e3aef930a71810a7e09b787103785eba",16);
	b[4][1] = new BigInteger("1081ae36550a67e59fed06668666e7c737db06a6beaf50149848c70832defdbf",16);
	b[5][0] = new BigInteger("10b9c74acec882199974f51a6dd71b53b5854269e9d3e8a042a996934bb5cc5e",16);
	b[5][1] = new BigInteger("21eb1c1470af761850c7f86200ca0a8ac77599248c4473a6572952d300207b24",16);
        
        Operations_Fpk op_Fpk = new Operations_Fpk();

        op_Fpk.squarFp12_V2(a);
        op_Fpk.squarFp12_V200(a);
*/
           
        
        
/*      // Test doubelemnt et Addition    
        BigInteger[][] q = new BigInteger[3][2]; 
        q[0][0] = new BigInteger("78e378d14f4984daa11c8cff456664bf17a4e1b83e5caf4944685436ba691ffd",16);
        q[0][1] = new BigInteger("71c9b34e26170a545b82b8b0aae136c1874eb754ad86ce0718ecd9023b895765",16);
        
        q[1][0] = new BigInteger("579bbd54094aa19636612a4b7ba11fd6af89c0ea19d3e8a33faa56934bb5cc60",16);
        q[1][1] = new BigInteger("21eb1c1470af761850c7f86200ca0a8ac77599248c4473a6572952d300207b24",16);
        
        q[2][0] = new BigInteger("1",16);
        q[2][1] = new BigInteger("0",16);
        
        BigInteger[][] r = new BigInteger[3][2]; 
        r[0][0] = new BigInteger("d8d119485e276bce743ba5a36cd321f73f79fd60f35a81e6fed8c681bbcf3e4d",16);
        r[0][1] = new BigInteger("81b73a47ea3b2ac4a95febc8cecbc0787a149d0554939de0e79e5bdd656cad69",16);
        
        r[1][0] = new BigInteger("415ca9164f3e17c9f46e5ec58544c546f3787f063aa0d3e392993141d0a4d914",16);
        r[1][1] = new BigInteger("70596996e3160588555c50876ac6adbfd26059ab7314d45cf7bccb640dd05734",16);
        
        r[2][0] = new BigInteger("83cb64c8abe1e253b3ad6b8d4fd390504e03e64a3359112668e7d68cd3718356",16);
        r[2][1] = new BigInteger("1cb1c93f9fefc51cd9a31e774b8f2e03f2aeceacca73323fc1bdfb952b3c175b",16);
        
        BigInteger[] p = new BigInteger[2];
        p[0] = new BigInteger("a54bd8689159d9e8b722d4a9ce727911782e469fd6a85a47ac7797a6c74240f8",16);
        p[1] = new BigInteger("d8d119485e276bce743ba5a36cd321f73f79fd60f35a81e6fed8c681bbcf3e4d",16);

        
        Operations_Fpk op_Fpk = new Operations_Fpk();
        //op_Fpk.doublingAndTangent( q, p);
        
        op_Fpk.additionAndLine(q, r, p);
*/        
/*       
        // Test Multiplcation Naive / Karatsuba / Tom Cook
        BigInteger[] a = new BigInteger[2]; 
        a[0] = new BigInteger("a54bd8689159d9e8b722d4a9ce727911782e469fd6a85a47ac7797a6c74240f8",16);
        Outils.affichierToTabBigInteger32Bit("a[0]",a[0]);
        a[1] = new BigInteger("3f8b3f4c99f87fa5b1b6581a8c23bafc68249e1e4a869861020bead6dd05ab9",16);
        Outils.affichierToTabBigInteger32Bit("a[1]",a[1]);
/*        a[2] = new BigInteger("9790dec8b7f5495384ddded2b77007d8addb525a60b7de375ccbcf343e08cd6a",16);
/*        //affichierToTabBigInteger32Bit("a[2]",a[2]);
        a[3] = new BigInteger("3af7b3cfc36f2278d30d89f12e8647d2c36ca40bd99562ad12e692266bfe3f9a",16);
        //affichierToTabBigInteger32Bit("a[3]",a[3]);
/*        a[4] = new BigInteger("ee92682bef52fdcde24d99b978b00c9bb9b55a0e4ec4f26fe10fd0ed6f2eb597",16);
        //affichierToTabBigInteger32Bit("a[4]",a[4]);
	a[5] = new BigInteger("485ca6307cc91351cd0f9dcf3b5f2dc94f54a086ce6a83d30759b39fd204d704",16);
        //affichierToTabBigInteger32Bit("a[5]",a[5]);
/*	a[6] = new BigInteger("20a99bfbc3171f89e5159a7e24d6ab2756225b13f6ccbdf27bcad0cbbb026f2d",16);
        //affichierToTabBigInteger32Bit("a[6]",a[6]);
	a[7] = new BigInteger("78e378d14f4984daa11c8cff456664bf17a4e1b83e5caf4944685436ba691ffd",16);
        //affichierToTabBigInteger32Bit("a[7]",a[7]);
	a[8] = new BigInteger("71c9b34e26170a545b82b8b0aae136c1874eb754ad86ce0718ecd9023b895765",16);
        //affichierToTabBigInteger32Bit("a[8]",a[8]);
	a[9] = new BigInteger("5330355d600379d3da562b75d0b363f9f12a82408c2400dae82735ba76dd1072",16);
        //affichierToTabBigInteger32Bit("a[9]",a[9]);
	a[10] = new BigInteger("8bdfb829bbb097e80336e00d6447089c587d142db28d0b37d74bb944d63040cb",16);
        //affichierToTabBigInteger32Bit("a[10]",a[10]);
	a[11] = new BigInteger("57cccdf401456330b3bb358a48d80d334471e3926cd97a122106f35333b7d2f3",16);
        //affichierToTabBigInteger32Bit("a[11]",a[11]);
*/       
/*        
        BigInteger[] b = new BigInteger[2];
        b[0] = new BigInteger("d8d119485e276bce743ba5a36cd321f73f79fd60f35a81e6fed8c681bbcf3e4d",16);
        Outils.affichierToTabBigInteger32Bit("b[0]",b[0]);
        b[1] = new BigInteger("81b73a47ea3b2ac4a95febc8cecbc0787a149d0554939de0e79e5bdd656cad69",16);
        Outils.affichierToTabBigInteger32Bit("b[1]",b[1]);
/*        b[2] = new BigInteger("415ca9164f3e17c9f46e5ec58544c546f3787f063aa0d3e392993141d0a4d914",16);
        //affichierToTabBigInteger32Bit("b[2]",b[2]);
/*        b[3] = new BigInteger("70596996e3160588555c50876ac6adbfd26059ab7314d45cf7bccb640dd05734",16);
        //affichierToTabBigInteger32Bit("b[3]",b[3]);
/*        b[4] = new BigInteger("83cb64c8abe1e253b3ad6b8d4fd390504e03e64a3359112668e7d68cd3718356",16);
        //affichierToTabBigInteger32Bit("b[4]",b[4]);
	b[5] = new BigInteger("1cb1c93f9fefc51cd9a31e774b8f2e03f2aeceacca73323fc1bdfb952b3c175b",16);
        //affichierToTabBigInteger32Bit("b[5]",b[5]);
/*	b[6] = new BigInteger("fe3b1309b14c72635c0b96874fda89609c266b3e923e69c4bdd888b7c8f7cfbd",16);
        //affichierToTabBigInteger32Bit("b[6]",b[6]);
	b[7] = new BigInteger("9fd29e8e86c84d7e5e6ca2a23371e369148bee70f84311438be3379f26697fd7",16);
        //affichierToTabBigInteger32Bit("b[7]",b[7]);
	b[8] = new BigInteger("d2a6bc54ed122362212847223fd722c954ba35711f1810af591d587103785ebf",16);
        //affichierToTabBigInteger32Bit("b[8]",b[8]);
	b[9] = new BigInteger("9e459a48ca0ea6ded9c570c8a1faf0cd2be403a71eaf501a924a470832defdc3",16);
        //affichierToTabBigInteger32Bit("b[9]",b[9]);
	b[10] = new BigInteger("579bbd54094aa19636612a4b7ba11fd6af89c0ea19d3e8a33faa56934bb5cc60",16);
        //affichierToTabBigInteger32Bit("b[10]",b[10]);
	b[11] = new BigInteger("21eb1c1470af761850c7f86200ca0a8ac77599248c4473a6572952d300207b24",16);
        //affichierToTabBigInteger32Bit("b[11]",b[11]);
*/        
/*        
        BigInteger[] c = new BigInteger[2];

        /* Génération des BigInteger random
        Random rnd = new Random();
        BigInteger u0 = new BigInteger(256,rnd);
        System.out.println("u0 = "+u0.toString(16));
        affichierToTabBigInteger32Bit("u0",u0);
        BigInteger u1 = new BigInteger(256,rnd);
        System.out.println("u1 = "+u1.toString(16));
        affichierToTabBigInteger32Bit("u1",u1);
        BigInteger v0 = new BigInteger(256,rnd);
        System.out.println("v0 = "+v0.toString(16));
        affichierToTabBigInteger32Bit("v0",v0);
        BigInteger v1 = new BigInteger(256,rnd);
        System.out.println("v1 = "+v1.toString(16));
        affichierToTabBigInteger32Bit("v1",v1);
        Random rnd = new Random();
        BigInteger a2 = new BigInteger(256,rnd);
        System.out.println("a2 = "+a2.toString(16));
        affichierToTabBigInteger32Bit("a2",a2);
        BigInteger b2 = new BigInteger(256,rnd);
        System.out.println("b2 = "+b2.toString(16));
        affichierToTabBigInteger32Bit("b2",b2);
        */
/*
        Operations_Fpk op_Fpk = new Operations_Fpk();
        
        int k=2;
        System.out.println("---------> Multiplication Naive ");
        c = op_Fpk.multiplicationNaive(a, b, k);
        System.out.println("c[0] = "+c[0].toString(16));
        //affichierToTabBigInteger32Bit("c[0]",c[0]); 
        System.out.println("c[1] = "+c[1].toString(16));
        //affichierToTabBigInteger32Bit("c[1]",c[1]);
/*        System.out.println("c[2] = "+c[2].toString(16));
        //affichierToTabBigInteger32Bit("c[2]",c[2]);
/*        System.out.println("c[3] = "+c[3].toString(16));
        //affichierToTabBigInteger32Bit("c[3]",c[3]);
/*        System.out.println("c[4] = "+c[4].toString(16));
        //affichierToTabBigInteger32Bit("c[4]",c[4]); 
        System.out.println("c[5] = "+c[5].toString(16));
        //affichierToTabBigInteger32Bit("c[5]",c[5]);
/*        System.out.println("c[6] = "+c[6].toString(16));
        //affichierToTabBigInteger32Bit("c[6]",c[6]);
        System.out.println("c[7] = "+c[7].toString(16));
        //affichierToTabBigInteger32Bit("c[7]",c[7]);
        System.out.println("c[8] = "+c[8].toString(16));
        //affichierToTabBigInteger32Bit("c[8]",c[8]); 
        System.out.println("c[9] = "+c[9].toString(16));
        //affichierToTabBigInteger32Bit("c[9]",c[9]);
        System.out.println("c[10] = "+c[10].toString(16));
        //affichierToTabBigInteger32Bit("c[10]",c[10]);
        System.out.println("c[11] = "+c[11].toString(16));
        //affichierToTabBigInteger32Bit("c[11]",c[11]);
 */      
/*        
        Operations_Fpk op_Fpk = new Operations_Fpk();
        Outils.affichierToTabBigInteger32Bit("p",op_Fpk.p);
         
        int k=2;
        c= op_Fpk.multiplicationNaive(a, b, k);
        System.out.println("---------> Multiplication Naive ");
        System.out.println("c[0] = "+c[0].toString(16));
        //affichierToTabBigInteger32Bit("c[0]",c[0]); 
        System.out.println("c[1] = "+c[1].toString(16));
        //affichierToTabBigInteger32Bit("c[1]",c[1]);        
        
        System.out.println("---------> Karatsuba 2 ");   
        op_Fpk.karatsuba2 (a, b, k );
        System.out.println("c[0] = "+c[0].toString(16));
        //affichierToTabBigInteger32Bit("c[0]",c[0]); 
        System.out.println("c[1] = "+c[1].toString(16));
        //affichierToTabBigInteger32Bit("c[1]",c[1]);        


/*
         Operations_Fpk op_Fpk = new Operations_Fpk();
         Outils outil = new Outils();
       
        //affichierToTabBigInteger32Bit("c[2]",c[2]);        
        //Affichage des paramêtres de Montgomry 
        System.out.println("p = "+op_Fpk.p.toString(16));
        outil.affichierToTabBigInteger32Bit("p",op_Fpk.p);
        System.out.println("R = "+op_Fpk.R.toString(16));
        outil.affichierToTabBigInteger32Bit("R",op_Fpk.R);
        System.out.println("R2 = "+op_Fpk.R2.toString(16));
        outil.affichierToTabBigInteger32Bit("R2",op_Fpk.R2);
        System.out.println("InvR = "+op_Fpk.InvR.toString(16));
        outil.affichierToTabBigInteger32Bit("InvR",op_Fpk.InvR);
        System.out.println("Np = "+op_Fpk.Np.toString(16));
        
*/
        


        
    }
    
}
