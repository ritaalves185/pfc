package com.example.spacemanager_app;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BaseDados {
    FirebaseDatabase db;
    DatabaseReference caracteristicas, lugares, lugares_caracteristicas, salas, sensores, sensores_estado, lugares_sensores;

    public BaseDados() {
        db = FirebaseDatabase.getInstance();
        lugares = db.getReference("lugares");
        DatabaseReference lugar11 = lugares.child("lugar1");
        DatabaseReference numero11 = lugar11.child("idLugar");
        numero11.setValue("1");
        DatabaseReference idSala11 = lugar11.child("idSala");
        idSala11.setValue("1");
        DatabaseReference reservado11 = lugar11.child("reservado");
        reservado11.setValue("nao");

        DatabaseReference lugar12 = lugares.child("lugar2");
        DatabaseReference numero12 = lugar12.child("idLugar");
        numero12.setValue("2");
        DatabaseReference idSala12 = lugar12.child("idSala");
        idSala12.setValue("1");

        DatabaseReference lugar13 = lugares.child("lugar3");
        DatabaseReference numero13 = lugar13.child("idLugar");
        numero13.setValue("3");
        DatabaseReference idSala13 = lugar13.child("idSala");
        idSala13.setValue("1");


        DatabaseReference lugar14 = lugares.child("lugar4");
        DatabaseReference numero14 = lugar14.child("idLugar");
        numero14.setValue("4");
        DatabaseReference idSala14 = lugar14.child("idSala");
        idSala14.setValue("1");
        DatabaseReference reservado14 = lugar14.child("reservado");
        reservado14.setValue("nao");

        DatabaseReference lugar15 = lugares.child("lugar5");
        DatabaseReference numero15 = lugar15.child("idLugar");
        numero15.setValue("5");
        DatabaseReference idSala15 = lugar15.child("idSala");
        idSala15.setValue("1");
        DatabaseReference reservado15 = lugar15.child("reservado");
        reservado15.setValue("nao");

        DatabaseReference lugar16 = lugares.child("lugar6");
        DatabaseReference numero16 = lugar16.child("idLugar");
        numero16.setValue("6");
        DatabaseReference idSala16 = lugar16.child("idSala");
        idSala16.setValue("1");
        DatabaseReference reservado16 = lugar16.child("reservado");
        reservado16.setValue("nao");

        DatabaseReference lugar17 = lugares.child("lugar7");
        DatabaseReference numero17 = lugar17.child("idLugar");
        numero17.setValue("7");
        DatabaseReference idSala17 = lugar17.child("idSala");
        idSala17.setValue("1");
        DatabaseReference reservado17 = lugar17.child("reservado");
        reservado17.setValue("nao");

        DatabaseReference lugar18 = lugares.child("lugar8");
        DatabaseReference numero18 = lugar18.child("idLugar");
        numero18.setValue("8");
        DatabaseReference idSala18 = lugar18.child("idSala");
        idSala18.setValue("1");
        DatabaseReference reservado18 = lugar18.child("reservado");
        reservado18.setValue("nao");

        DatabaseReference lugar19 = lugares.child("lugar9");
        DatabaseReference numero19 = lugar19.child("idLugar");
        numero19.setValue("9");
        DatabaseReference idSala19 = lugar19.child("idSala");
        idSala19.setValue("1");
        DatabaseReference reservado19 = lugar19.child("reservado");
        reservado19.setValue("nao");

        DatabaseReference lugar110 = lugares.child("lugar10");
        DatabaseReference numero110 = lugar110.child("idLugar");
        numero110.setValue("10");
        DatabaseReference idSala110 = lugar110.child("idSala");
        idSala110.setValue("1");
        DatabaseReference reservado110 = lugar110.child("reservado");
        reservado110.setValue("nao");

        DatabaseReference lugar111 = lugares.child("lugar11");
        DatabaseReference numero111 = lugar111.child("idLugar");
        numero111.setValue("11");
        DatabaseReference idSala111 = lugar111.child("idSala");
        idSala111.setValue("1");
        DatabaseReference reservado111 = lugar111.child("reservado");
        reservado111.setValue("nao");

        DatabaseReference lugar112 = lugares.child("lugar12");
        DatabaseReference numero112 = lugar112.child("idLugar");
        numero112.setValue("12");
        DatabaseReference idSala112 = lugar112.child("idSala");
        idSala112.setValue("1");
        DatabaseReference reservado112 = lugar112.child("reservado");
        reservado112.setValue("nao");
        /**
        *
        */
        lugares_caracteristicas = db.getReference("lugares_caracteristicas");
        DatabaseReference lc1 = lugares_caracteristicas.child("lugar1");
        DatabaseReference lcn1 = lc1.child("idLugar");
        lcn1.setValue("1");
        DatabaseReference lcid1 = lc1.child("idSala");
        lcid1.setValue("1");
        DatabaseReference lccarac11 = lc1.child("idCaracteristica");
        lccarac11.setValue("1");
        DatabaseReference lccarac12 = lc1.child("idCaracteristica");
        lccarac12.setValue("2");

        DatabaseReference lc2 = lugares_caracteristicas.child("lugar2");
        DatabaseReference lcn2 = lc2.child("idLugar");
        lcn2.setValue("2");
        DatabaseReference lcid2 = lc2.child("idSala");
        lcid2.setValue("1");
        DatabaseReference lccarac21 = lc2.child("idCaracteristica");
        lccarac21.setValue("1");

        DatabaseReference lc3 = lugares_caracteristicas.child("lugar3");
        DatabaseReference lcn3 = lc3.child("idLugar");
        lcn3.setValue("3");
        DatabaseReference lcid3 = lc2.child("idSala");
        lcid3.setValue("1");

        DatabaseReference lc4 = lugares_caracteristicas.child("lugar4");
        DatabaseReference lcn4 = lc4.child("idLugar");
        lcn4.setValue("4");
        DatabaseReference lcid4 = lc4.child("idSala");
        lcid4.setValue("1");

        DatabaseReference lc5 = lugares_caracteristicas.child("lugar5");
        DatabaseReference lcn5 = lc5.child("idLugar");
        lcn5.setValue("5");
        DatabaseReference lcid5 = lc5.child("idSala");
        lcid5.setValue("1");
        DatabaseReference lccarac51 = lc5.child("idCaracteristica");
        lccarac51.setValue("1");
        DatabaseReference lccarac52 = lc5.child("idCaracteristica");
        lccarac52.setValue("2");

        DatabaseReference lc6 = lugares_caracteristicas.child("lugar6");
        DatabaseReference lcn6 = lc6.child("idLugar");
        lcn6.setValue("6");
        DatabaseReference lcid6 = lc6.child("idSala");
        lcid6.setValue("1");
        DatabaseReference lccarac61 = lc6.child("idCaracteristica");
        lccarac61.setValue("1");

        DatabaseReference lc7 = lugares_caracteristicas.child("lugar7");
        DatabaseReference lcn7 = lc7.child("idLugar");
        lcn7.setValue("7");
        DatabaseReference lcid7 = lc7.child("idSala");
        lcid7.setValue("1");

        DatabaseReference lc8 = lugares_caracteristicas.child("lugar8");
        DatabaseReference lcn8 = lc8.child("idLugar");
        lcn8.setValue("8");
        DatabaseReference lcid8 = lc8.child("idSala");
        lcid8.setValue("1");


        DatabaseReference lc9 = lugares_caracteristicas.child("lugar9");
        DatabaseReference lcn9 = lc9.child("idLugar");
        lcn9.setValue("9");
        DatabaseReference lcid9 = lc9.child("idSala");
        lcid9.setValue("1");
        DatabaseReference lccarac91 = lc9.child("idCaracteristica");
        lccarac91.setValue("2");

        DatabaseReference lc10 = lugares_caracteristicas.child("lugar10");
        DatabaseReference lcn10 = lc10.child("idLugar");
        lcn10.setValue("10");
        DatabaseReference lcid10 = lc10.child("idSala");
        lcid10.setValue("1");

        DatabaseReference lc11 = lugares_caracteristicas.child("lugar11");
        DatabaseReference lcn11 = lc11.child("idLugar");
        lcn11.setValue("11");
        DatabaseReference lcid11 = lc11.child("idSala");
        lcid11.setValue("1");

        DatabaseReference lc12 = lugares_caracteristicas.child("lugar12");
        DatabaseReference lcn12 = lc12.child("idLugar");
        lcn12.setValue("12");
        DatabaseReference lcid12 = lc12.child("idSala");
        lcid12.setValue("1");

        /**
         *
         */
        caracteristicas = db.getReference("caracteristicas");
        DatabaseReference carac1 = caracteristicas.child("caracteristica1");
        DatabaseReference idc1 = carac1.child("id");
        idc1.setValue("1");
        DatabaseReference descc1 = carac1.child("descricao");
        descc1.setValue("monitor");
        DatabaseReference carac2 = caracteristicas.child("caracteristica2");
        DatabaseReference idc2 = carac2.child("id");
        idc1.setValue("2");
        DatabaseReference descc2 = carac2.child("descricao");
        descc2.setValue("janela");
        /**
         *
         */
        salas = db.getReference("salas");
        DatabaseReference sala1 = salas.child("sala1");
        DatabaseReference id1 = sala1.child("id");
        id1.setValue("1");
        DatabaseReference piso1 = sala1.child("piso");
        piso1.setValue("1");
        DatabaseReference desc1 = sala1.child("descricao");
        desc1.setValue("piso 1, sala 1");

        DatabaseReference sala2 = salas.child("sala2");
        DatabaseReference id2 = sala2.child("id");
        id2.setValue("2");
        DatabaseReference piso2 = sala2.child("piso");
        piso2.setValue("1");
        DatabaseReference desc2 = sala2.child("descricao");
        desc2.setValue("piso 1, sala 2");

        DatabaseReference sala3 = salas.child("sala3");
        DatabaseReference id3 = sala3.child("id");
        id3.setValue("3");
        DatabaseReference piso3 = sala3.child("piso");
        piso3.setValue("2");
        DatabaseReference desc3 = sala3.child("descricao");
        desc3.setValue("piso 2, sala 1");

        DatabaseReference sala4 = salas.child("sala4");
        DatabaseReference id4 = sala4.child("id");
        id4.setValue("4");
        DatabaseReference piso4 = sala4.child("piso");
        piso4.setValue("2");
        DatabaseReference desc4 = sala4.child("descricao");
        desc4.setValue("piso 2, sala 2");
        /**
         *
         */
        sensores = db.getReference("sensores");
        DatabaseReference sensor1 = sensores.child("sensor1");
        DatabaseReference ids1 = sensor1.child("idSensor");
        ids1.setValue("S1");
        DatabaseReference url1 = sensor1.child("url");
        url1.setValue("urlsensor1");

        DatabaseReference sensor2 = sensores.child("sensor2");
        DatabaseReference ids2 = sensor2.child("idSensor");
        ids2.setValue("S2");
        DatabaseReference url2 = sensor2.child("url");
        url2.setValue("urlsensor2");

        DatabaseReference sensor3 = sensores.child("sensor3");
        DatabaseReference ids3 = sensor3.child("idSensor");
        ids3.setValue("S3");
        DatabaseReference url3 = sensor3.child("url");
        url3.setValue("urlsensor3");

        DatabaseReference sensor4 = sensores.child("sensor4");
        DatabaseReference ids4 = sensor4.child("idSensor");
        ids4.setValue("S4");
        DatabaseReference url4 = sensor4.child("url");
        url4.setValue("urlsensor4");

        DatabaseReference sensor5 = sensores.child("sensor5");
        DatabaseReference ids5 = sensor5.child("idSensor");
        ids5.setValue("S5");
        DatabaseReference url5 = sensor5.child("url");
        url5.setValue("urlsensor5");

        DatabaseReference sensor6 = sensores.child("sensor6");
        DatabaseReference ids6 = sensor6.child("idSensor");
        ids6.setValue("S6");
        DatabaseReference url6 = sensor6.child("url");
        url6.setValue("urlsensor6");

        DatabaseReference sensor7 = sensores.child("sensor7");
        DatabaseReference ids7 = sensor7.child("idSensor");
        ids7.setValue("S7");
        DatabaseReference url7 = sensor7.child("url");
        url7.setValue("urlsensor7");

        DatabaseReference sensor8 = sensores.child("sensor8");
        DatabaseReference ids8 = sensor8.child("idSensor");
        ids8.setValue("S8");
        DatabaseReference url8 = sensor8.child("url");
        url8.setValue("urlsensor8");

        DatabaseReference sensor9 = sensores.child("sensor9");
        DatabaseReference ids9 = sensor9.child("idSensor");
        ids9.setValue("S9");
        DatabaseReference url9 = sensor9.child("url");
        url9.setValue("urlsensor9");

        DatabaseReference sensor10 = sensores.child("sensor10");
        DatabaseReference ids10 = sensor10.child("idSensor");
        ids10.setValue("S10");
        DatabaseReference url10 = sensor10.child("url");
        url10.setValue("urlsensor10");

        DatabaseReference sensor11 = sensores.child("sensor11");
        DatabaseReference ids11 = sensor11.child("idSensor");
        ids11.setValue("S11");
        DatabaseReference url11 = sensor11.child("url");
        url11.setValue("urlsensor11");

        DatabaseReference sensor12 = sensores.child("sensor12");
        DatabaseReference ids12 = sensor12.child("idSensor");
        ids12.setValue("S12");
        DatabaseReference url12 = sensor12.child("url");
        url12.setValue("urlsensor12");
        /**
         *
         */
        sensores_estado = db.getReference("sensores_estado");
        DatabaseReference se1 = sensores_estado.child("sensor1");
        DatabaseReference seurl1 = se1.child("idSensor");
        seurl1.setValue("S1");
        DatabaseReference seocupado1 = se1.child("ocupado");
        seocupado1.setValue("sim");

        DatabaseReference se2 = sensores_estado.child("sensor2");
        DatabaseReference seurl2 = se2.child("idSensor");
        seurl2.setValue("S2");
        DatabaseReference seocupado2 = se2.child("ocupado");
        seocupado2.setValue("nao");

        DatabaseReference se3 = sensores_estado.child("sensor3");
        DatabaseReference seurl3 = se3.child("idSensor");
        seurl3.setValue("S3");
        DatabaseReference seocupado3 = se3.child("ocupado");
        seocupado3.setValue("nao");

        DatabaseReference se4 = sensores_estado.child("sensor4");
        DatabaseReference seurl4 = se4.child("idSensor");
        seurl4.setValue("S4");
        DatabaseReference seocupado4 = se4.child("ocupado");
        seocupado4.setValue("nao");

        DatabaseReference se5 = sensores_estado.child("sensor5");
        DatabaseReference seurl5 = se5.child("idSensor");
        seurl5.setValue("S5");
        DatabaseReference seocupado5 = se5.child("ocupado");
        seocupado5.setValue("nao");

        DatabaseReference se6 = sensores_estado.child("sensor6");
        DatabaseReference seurl6 = se6.child("idSensor");
        seurl6.setValue("S6");
        DatabaseReference seocupado6 = se6.child("ocupado");
        seocupado6.setValue("nao");

        DatabaseReference se7 = sensores_estado.child("sensor7");
        DatabaseReference seurl7 = se7.child("idSensor");
        seurl7.setValue("S7");
        DatabaseReference seocupado7 = se7.child("ocupado");
        seocupado7.setValue("sim");

        DatabaseReference se8 = sensores_estado.child("sensor8");
        DatabaseReference seurl8 = se8.child("idSensor");
        seurl8.setValue("S8");
        DatabaseReference seocupado8 = se8.child("ocupado");
        seocupado8.setValue("nao");

        DatabaseReference se9 = sensores_estado.child("sensor9");
        DatabaseReference seurl9 = se9.child("idSensor");
        seurl9.setValue("S9");
        DatabaseReference seocupado9 = se9.child("ocupado");
        seocupado9.setValue("nao");

        DatabaseReference se10 = sensores_estado.child("sensor10");
        DatabaseReference seurl10 = se10.child("idSensor");
        seurl10.setValue("S10");
        DatabaseReference seocupado10 = se10.child("ocupado");
        seocupado10.setValue("sim");

        DatabaseReference se11 = sensores_estado.child("sensor11");
        DatabaseReference seurl11 = se11.child("idSensor");
        seurl11.setValue("S11");
        DatabaseReference seocupado11 = se11.child("ocupado");
        seocupado11.setValue("nao");

        DatabaseReference se12 = sensores_estado.child("sensor12");
        DatabaseReference seurl12 = se12.child("idSensor");
        seurl12.setValue("S12");
        DatabaseReference seocupado12 = se12.child("ocupado");
        seocupado12.setValue("nao");
        /**
         *
         */
        lugares_sensores = db.getReference("lugar_sensor");
        DatabaseReference ls1 = lugares_sensores.child("lugar1");
        DatabaseReference lsid1 = ls1.child("idLugar");
        lsid1.setValue("1");
        DatabaseReference lsids1 = ls1.child("idSala");
        lsids1.setValue("1");
        DatabaseReference lsidse1 = ls1.child("idSensor");
        lsidse1.setValue("S1");

        DatabaseReference ls2 = lugares_sensores.child("lugar2");
        DatabaseReference lsid2 = ls2.child("idLugar");
        lsid2.setValue("2");
        DatabaseReference lsids2 = ls2.child("idSala");
        lsids2.setValue("1");
        DatabaseReference lsidse2 = ls2.child("idSensor");
        lsidse2.setValue("S2");

        DatabaseReference ls3 = lugares_sensores.child("lugar3");
        DatabaseReference lsid3 = ls3.child("idLugar");
        lsid3.setValue("3");
        DatabaseReference lsids3 = ls3.child("idSala");
        lsids3.setValue("1");
        DatabaseReference lsidse3 = ls3.child("idSensor");
        lsidse3.setValue("S3");

        DatabaseReference ls4 = lugares_sensores.child("lugar4");
        DatabaseReference lsid4 = ls4.child("idLugar");
        lsid4.setValue("4");
        DatabaseReference lsids4 = ls4.child("idSala");
        lsids4.setValue("1");
        DatabaseReference lsidse4 = ls4.child("idSensor");
        lsidse4.setValue("S4");

        DatabaseReference ls5 = lugares_sensores.child("lugar5");
        DatabaseReference lsid5 = ls5.child("idLugar");
        lsid5.setValue("5");
        DatabaseReference lsids5 = ls5.child("idSala");
        lsids5.setValue("1");
        DatabaseReference lsidse5 = ls5.child("idSensor");
        lsidse5.setValue("S5");

        DatabaseReference ls6 = lugares_sensores.child("lugar6");
        DatabaseReference lsid6 = ls6.child("idLugar");
        lsid6.setValue("6");
        DatabaseReference lsids6 = ls6.child("idSala");
        lsids6.setValue("1");
        DatabaseReference lsidse6 = ls6.child("idSensor");
        lsidse6.setValue("S6");

        DatabaseReference ls7 = lugares_sensores.child("lugar7");
        DatabaseReference lsid7 = ls7.child("idLugar");
        lsid7.setValue("7");
        DatabaseReference lsids7 = ls7.child("idSala");
        lsids7.setValue("1");
        DatabaseReference lsidse7 = ls7.child("idSensor");
        lsidse7.setValue("S7");

        DatabaseReference ls8 = lugares_sensores.child("lugar8");
        DatabaseReference lsid8 = ls8.child("idLugar");
        lsid8.setValue("8");
        DatabaseReference lsids8 = ls8.child("idSala");
        lsids8.setValue("1");
        DatabaseReference lsidse8 = ls8.child("idSensor");
        lsidse8.setValue("S8");

        DatabaseReference ls9 = lugares_sensores.child("lugar9");
        DatabaseReference lsid9 = ls9.child("idLugar");
        lsid9.setValue("9");
        DatabaseReference lsids9 = ls9.child("idSala");
        lsids9.setValue("1");
        DatabaseReference lsidse9 = ls9.child("idSensor");
        lsidse9.setValue("S9");

        DatabaseReference ls10 = lugares_sensores.child("lugar10");
        DatabaseReference lsid10 = ls10.child("idLugar");
        lsid10.setValue("10");
        DatabaseReference lsids10 = ls10.child("idSala");
        lsids10.setValue("1");
        DatabaseReference lsidse10 = ls10.child("idSensor");
        lsidse10.setValue("S10");

        DatabaseReference ls11 = lugares_sensores.child("lugar11");
        DatabaseReference lsid11 = ls11.child("idLugar");
        lsid11.setValue("11");
        DatabaseReference lsids11 = ls11.child("idSala");
        lsids11.setValue("1");
        DatabaseReference lsidse11 = ls11.child("idSensor");
        lsidse11.setValue("S11");

        DatabaseReference ls12 = lugares_sensores.child("lugar12");
        DatabaseReference lsid12 = ls12.child("idLugar");
        lsid12.setValue("12");
        DatabaseReference lsids12 = ls12.child("idSala");
        lsids12.setValue("1");
        DatabaseReference lsidse12 = ls12.child("idSensor");
        lsidse12.setValue("S12");
    }
}
