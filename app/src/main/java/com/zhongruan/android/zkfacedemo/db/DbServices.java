package com.zhongruan.android.zkfacedemo.db;

import android.content.Context;
import android.util.Log;

import com.zhongruan.android.zkfacedemo.db.entity.Bk_ks;
import com.zhongruan.android.zkfacedemo.db.entity.Bk_ks_cjxx;
import com.zhongruan.android.zkfacedemo.db.entity.Bk_ks_temp;
import com.zhongruan.android.zkfacedemo.db.entity.Ks_cc;
import com.zhongruan.android.zkfacedemo.db.entity.Ks_kc;
import com.zhongruan.android.zkfacedemo.db.entity.Ks_kd;
import com.zhongruan.android.zkfacedemo.db.entity.Ks_km;
import com.zhongruan.android.zkfacedemo.db.entity.Sb_ip;
import com.zhongruan.android.zkfacedemo.db.entity.Sfrz_rzfs;
import com.zhongruan.android.zkfacedemo.db.entity.Sfrz_rzjg;
import com.zhongruan.android.zkfacedemo.db.entity.Sfrz_rzjl;
import com.zhongruan.android.zkfacedemo.db.entity.Sfrz_rzzt;
import com.zhongruan.android.zkfacedemo.db.entity.Sn_number;
import com.zhongruan.android.zkfacedemo.ui.MyApplication;
import com.zhongruan.android.zkfacedemo.utils.LogUtil;

import java.util.List;


/**
 * Created by castl on 2016/5/20.
 */
public class DbServices {
    private static final String TAG = "HNZR";
    private static DbServices instance;
    private static Context appContext;
    private DaoSession mDaoSession;

    private Bk_ks_cjxxDao bk_ks_cjxxDao;
    private Bk_ks_tempDao bk_ks_tempDao;
    private Ks_ccDao ks_ccDao;
    private Ks_kcDao ks_kcDao;
    private Ks_kdDao ks_kdDao;
    private Ks_kmDao ks_kmDao;
    private Bk_ksxpDao bk_ksxpDao;
    private Kstz_zwDao kstz_zwDao;
    private Sfrz_rzztDao rzztDao;
    private Sfrz_rzfsDao rzfsDao;
    private Sfrz_rzjlDao rzjlDao;
    private Sfrz_rzjgDao rzjgDao;
    private Bk_ksDao bk_ksDao;
    private Sb_ipDao sbIpDao;
    private Sn_numberDao sn_numberDao;


    private DbServices() {
    }

    /**
     * 采用单例模式
     *
     * @param context 上下文
     * @return dbservice
     */
    public static DbServices getInstance(Context context) {
        if (instance == null) {
            instance = new DbServices();
            if (appContext == null) {
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = MyApplication.getDaoInstant(context);
            instance.bk_ks_cjxxDao = instance.mDaoSession.getBk_ks_cjxxDao();
            instance.bk_ks_tempDao = instance.mDaoSession.getBk_ks_tempDao();

            instance.ks_ccDao = instance.mDaoSession.getKs_ccDao();
            instance.ks_kcDao = instance.mDaoSession.getKs_kcDao();

            instance.ks_kdDao = instance.mDaoSession.getKs_kdDao();
            instance.ks_kmDao = instance.mDaoSession.getKs_kmDao();
            instance.bk_ksxpDao = instance.mDaoSession.getBk_ksxpDao();
            instance.kstz_zwDao = instance.mDaoSession.getKstz_zwDao();
            instance.rzztDao = instance.mDaoSession.getSfrz_rzztDao();
            instance.rzfsDao = instance.mDaoSession.getSfrz_rzfsDao();
            instance.rzjlDao = instance.mDaoSession.getSfrz_rzjlDao();
            instance.rzjgDao = instance.mDaoSession.getSfrz_rzjgDao();
            instance.bk_ksDao = instance.mDaoSession.getBk_ksDao();
            instance.sbIpDao = instance.mDaoSession.getSb_ipDao();
            instance.sn_numberDao = instance.mDaoSession.getSn_numberDao();

        }
        return instance;
    }


    /**
     * 取出所有数据
     *
     * @return 所有数据信息
     */
    public List<Bk_ks_cjxx> loadAllNote() {
        return bk_ks_cjxxDao.loadAll();
    }

    public List<Bk_ks_temp> loadAlltemp() {
        return bk_ks_tempDao.loadAll();
    }

    public List<Ks_cc> loadAllcc() {
        return ks_ccDao.queryBuilder().orderAsc(Ks_ccDao.Properties.Cc_no).list();
    }

    public List<Ks_kc> loadAllkc() {
        return ks_kcDao.loadAll();
    }

    public List<Ks_kd> loadAllkd() {
        return ks_kdDao.loadAll();
    }

    public List<Ks_km> loadAllkm() {
        return ks_kmDao.loadAll();
    }


    public List<Sfrz_rzfs> loadAllrzfs() {
        return rzfsDao.loadAll();
    }

    public List<Sfrz_rzzt> loadAllrzzt() {
        return rzztDao.loadAll();
    }

    public List<Sfrz_rzjg> loadAllrzjg() {
        return rzjgDao.loadAll();
    }

    public List<Sfrz_rzjl> loadAllrzjl() {
        return rzjlDao.loadAll();
    }

    public List<Bk_ks> loadAllbkks() {
        return bk_ksDao.loadAll();
    }

    public List<Sb_ip> loadAllsbip() {
        return sbIpDao.loadAll();
    }

    public List<Sn_number> loadAllSN() {
        return sn_numberDao.loadAll();
    }

    /**
     * 根据查询条件,返回数据列表
     *
     * @param where 条件
     * @return 数据列表
     */


    public List<Bk_ks_cjxx> querySfzh(String where) {
        return bk_ks_cjxxDao.queryBuilder().where(Bk_ks_cjxxDao.Properties.Sfz_sfzh.eq(where))
                .orderDesc(Bk_ks_cjxxDao.Properties.Id)
                .build().list();
    }


    /**
     * 根据查询条件,返回数据列表
     *
     * @param isSbzt 条件
     * @return 数据列表
     */


    public List<Bk_ks_cjxx> querySbzt(int isSbzt) {
        return bk_ks_cjxxDao.queryBuilder().where(Bk_ks_cjxxDao.Properties.IsSbzt.eq(isSbzt))
                .orderDesc(Bk_ks_cjxxDao.Properties.Id)
                .build().list();
    }

    public boolean queryKC(String kc) {
        if (ks_kcDao.queryBuilder().where(Ks_kcDao.Properties.Kc_extract.eq(kc)).list().size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public List<Sfrz_rzjg> selectCCrzjg(String ccmc) {
        return rzjgDao.queryBuilder().where(Sfrz_rzjgDao.Properties.Rzjg_kmno.eq(ks_ccDao.queryBuilder().where(Ks_ccDao.Properties.Cc_name.eq(ccmc)).list().get(0).getKm_no())).list();
    }

    public List<Ks_kc> selectKC() {
        return ks_kcDao.queryBuilder().where(Ks_kcDao.Properties.Kc_extract.eq("1")).list();
    }

    public List<Bk_ks> queryBKKSList(String ks_kcmc, String ks_ccmc) {
        return bk_ksDao.queryBuilder().where(Bk_ksDao.Properties.Ks_ccmc.eq(ks_ccmc)).where(Bk_ksDao.Properties.Ks_kcmc.eq(ks_kcmc)).orderAsc(Bk_ksDao.Properties.Ks_zwh).list();
    }

    public List<Bk_ks> queryBKKSLists(String ks_ccmc) {
        return bk_ksDao.queryBuilder().where(Bk_ksDao.Properties.Ks_ccmc.eq(ks_ccmc)).orderAsc(Bk_ksDao.Properties.Ks_zwh).list();
    }

    public List<Bk_ks> queryNOBKKSList(String ks_kcmc, String ks_ccmc) {
        return bk_ksDao.queryBuilder().whereOr(Bk_ksDao.Properties.Ks_ccmc.notEq(ks_ccmc), Bk_ksDao.Properties.Ks_kcmc.notEq(ks_kcmc)).orderAsc(Bk_ksDao.Properties.Ks_ccno).list();
    }


    public String selectIP() {
        return sbIpDao.queryBuilder().where(Sb_ipDao.Properties.Ipid.eq("1")).list().get(0).getSb_ip();
    }

    public List<Ks_cc> selectCC() {
        return ks_ccDao.queryBuilder().where(Ks_ccDao.Properties.Cc_extract.eq("1")).list();
    }


    public Bk_ks selectKszh(String ccno, String zh) {
        return bk_ksDao.queryBuilder().where(Bk_ksDao.Properties.Ks_ccno.eq(ccno)).where(Bk_ksDao.Properties.Ks_zwh.eq(zh)).orderAsc(Bk_ksDao.Properties.Ks_zwh).list().get(0);
    }


    public String selectRzjg(String ksno) {
        return rzjgDao.queryBuilder().where(Sfrz_rzjgDao.Properties.Rzjg_ksno.eq(ksno)).list().get(0).getRzjgid();
    }


    public int queryBkKsIsTG(String ks_kcmc, String ks_ccmc, String isRZ) {
        return bk_ksDao.queryBuilder().where(Bk_ksDao.Properties.Ks_kcmc.eq(ks_kcmc)).where(Bk_ksDao.Properties.Ks_ccmc.eq(ks_ccmc)).where(Bk_ksDao.Properties.IsRZ.eq(isRZ)).distinct().list().size();
    }

    public int queryBkKsIsTGs(String ks_ccmc, String isRZ) {
        return bk_ksDao.queryBuilder().where(Bk_ksDao.Properties.Ks_ccmc.eq(ks_ccmc)).where(Bk_ksDao.Properties.IsRZ.eq(isRZ)).distinct().list().size();
    }


    public List<Sfrz_rzjg> selectRZJGSB(String ccmc) {
        return rzjgDao.queryBuilder().where(Sfrz_rzjgDao.Properties.Rzjg_kmno.eq(ks_ccDao.queryBuilder().where(Ks_ccDao.Properties.Cc_name.eq(ccmc)).list().get(0).getKm_no()), Sfrz_rzjgDao.Properties.Rzjg_sb.notEq("1")).list();
    }

    public List<Sfrz_rzjl> selectRZJLSB() {
        return rzjlDao.queryBuilder().where(Sfrz_rzjlDao.Properties.Rzjl_sb.notEq("1")).list();
    }


    public Bk_ks selectBKKS(String kcmc, String ccno, String zjno) {
        return bk_ksDao.queryBuilder().where(Bk_ksDao.Properties.Ks_kcmc.eq(kcmc)).where(Bk_ksDao.Properties.Ks_ccno.eq(ccno)).where(Bk_ksDao.Properties.Ks_zjno.eq(zjno)).build().unique();
    }

    public Bk_ks selectBKKSs(String ccno, String zjno) {
        return bk_ksDao.queryBuilder().where(Bk_ksDao.Properties.Ks_ccno.eq(ccno)).where(Bk_ksDao.Properties.Ks_zjno.eq(zjno)).build().unique();
    }

    public List<Bk_ks_temp> selectDOWNBKKS(String kcno, String ccno) {
        return bk_ks_tempDao.queryBuilder().where(Bk_ks_tempDao.Properties.Kcno.eq(kcno), Bk_ks_tempDao.Properties.Ccno.eq(ccno)).build().list();
    }

    public List<Sfrz_rzjl> selectRZJL(String ksno) {
        return rzjlDao.queryBuilder().where(Sfrz_rzjlDao.Properties.Rzjl_ksno.eq(ksno)).build().list();
    }

    /**
     * 插入
     */
    public long saveNote(String sfz_cardNo, String sfz_xm, String sfz_xb,
                         String sfz_mz, String sfz_birth, String sfz_address, String sfz_sfzh,
                         String sfz_picpath, String sfz_qfjg, String sfz_yxjsrq,
                         String sfz_yxksrq, String zw_picpath, String zw_features,
                         int zw_quality, String rl_picpath, String cjTime, int isSbzt) {
        Bk_ks_cjxx bk_ks_cjxx = new Bk_ks_cjxx();
        bk_ks_cjxx.setSfz_cardNo(sfz_cardNo);
        bk_ks_cjxx.setSfz_xm(sfz_xm);
        bk_ks_cjxx.setSfz_xb(sfz_xb);
        bk_ks_cjxx.setSfz_mz(sfz_mz);
        bk_ks_cjxx.setSfz_birth(sfz_birth);
        bk_ks_cjxx.setSfz_address(sfz_address);
        bk_ks_cjxx.setSfz_sfzh(sfz_sfzh);
        bk_ks_cjxx.setSfz_picpath(sfz_picpath);
        bk_ks_cjxx.setSfz_qfjg(sfz_qfjg);
        bk_ks_cjxx.setSfz_yxjsrq(sfz_yxjsrq);
        bk_ks_cjxx.setSfz_yxksrq(sfz_yxksrq);
        bk_ks_cjxx.setZw_features(zw_features);
        bk_ks_cjxx.setZw_quality(zw_quality);
        bk_ks_cjxx.setZw_picpath(zw_picpath);
        bk_ks_cjxx.setRl_picpath(rl_picpath);
        bk_ks_cjxx.setCjTime(cjTime);
        bk_ks_cjxx.setIsSbzt(isSbzt);
        return bk_ks_cjxxDao.insert(bk_ks_cjxx);
    }


    public long saveRzjl(String rzjl_rzfsno, String rzjl_ksno, String rzjl_kmbh, String rzjl_kdbh, String rzjl_kcbh, String rzjl_zwh, String rzjl_device, String rzjl_verify_result, String rzjl_time, String rzjl_Features, String rzjl_pith, String rzjl_rzjgid, String rzjl_sb) {

        Sfrz_rzjl sfrzRzjl = new Sfrz_rzjl();
        sfrzRzjl.setRzjl_rzfsno(rzjl_rzfsno);
        sfrzRzjl.setRzjl_ksno(rzjl_ksno);
        sfrzRzjl.setRzjl_kmbh(rzjl_kmbh);
        sfrzRzjl.setRzjl_kdbh(rzjl_kdbh);
        sfrzRzjl.setRzjl_kcbh(rzjl_kcbh);
        sfrzRzjl.setRzjl_zwh(rzjl_zwh);
        sfrzRzjl.setRzjl_device(rzjl_device);
        sfrzRzjl.setRzjl_verify_result(rzjl_verify_result);
        sfrzRzjl.setRzjl_time(rzjl_time);
        sfrzRzjl.setRzjl_Features(rzjl_Features);
        sfrzRzjl.setRzjl_pith(rzjl_pith);
        sfrzRzjl.setRzjl_rzjgid(rzjl_rzjgid);
        sfrzRzjl.setRzjl_sb(rzjl_sb);
        return rzjlDao.insert(sfrzRzjl);
    }

    public long saveRzjg(String rzjg_ztid, String rzjg_ksno, String rzjg_kmno, String rzjg_kdno, String rzjg_kcno, String rzjg_zwh, String rzjg_device, String rzjg_time, String rzjg_sb) {
        Sfrz_rzjg sfrzRzjg = new Sfrz_rzjg();
        sfrzRzjg.setRzjg_ztid(rzjg_ztid);
        sfrzRzjg.setRzjg_ksno(rzjg_ksno);
        sfrzRzjg.setRzjg_kmno(rzjg_kmno);
        sfrzRzjg.setRzjg_kdno(rzjg_kdno);
        sfrzRzjg.setRzjg_kcno(rzjg_kcno);
        sfrzRzjg.setRzjg_zwh(rzjg_zwh);
        sfrzRzjg.setRzjg_device(rzjg_device);
        sfrzRzjg.setRzjg_time(rzjg_time);
        sfrzRzjg.setRzjg_sb(rzjg_sb);
        return rzjgDao.insert(sfrzRzjg);
    }

    public void saveKsKc(String kcName) {
        Ks_kc ks_kc = ks_kcDao.queryBuilder().where(Ks_kcDao.Properties.Kc_name.eq(kcName)).build().unique();
        if (ks_kc != null) {
            ks_kc.setKc_extract("1");
            ks_kcDao.update(ks_kc);
            LogUtil.i("修改成功");
        } else {
            LogUtil.i("数据不存在");
        }
    }

    public void saveKsCc(String ccName) {
        Ks_cc ks_cc = ks_ccDao.queryBuilder().where(Ks_ccDao.Properties.Cc_name.eq(ccName)).build().unique();
        if (ks_cc != null) {
            ks_cc.setCc_extract("1");
            ks_ccDao.update(ks_cc);
            LogUtil.i("修改成功");
        } else {
            LogUtil.i("数据不存在");
        }
    }

    public void saveSbip(String ipStr) {
        Sb_ip sbIp = sbIpDao.queryBuilder().where(Sb_ipDao.Properties.Ipid.eq(1)).build().unique();
        if (sbIp != null) {
            sbIp.setSb_ip(ipStr);
            sbIpDao.update(sbIp);
            LogUtil.i("修改成功");
        } else {
            LogUtil.i("数据不存在");
        }
    }

    public void saveBkKs(String ks_kcmc, String ks_ccno, String zjno) {
        List<Bk_ks> bk_ks = bk_ksDao.queryBuilder().where(Bk_ksDao.Properties.Ks_kcmc.eq(ks_kcmc)).where(Bk_ksDao.Properties.Ks_ccno.eq(ks_ccno)).where(Bk_ksDao.Properties.Ks_zjno.eq(zjno)).list();
        LogUtil.i(zjno);
        LogUtil.i(bk_ks);
        LogUtil.i(bk_ks.size());
        for (int i = 0; i < bk_ks.size(); i++) {
            if (bk_ks.get(i) != null) {
                bk_ks.get(i).setIsRZ("1");
                bk_ksDao.update(bk_ks.get(i));
                LogUtil.i("修改成功");
            } else {
                LogUtil.i("数据不存在");
            }
        }
    }

    public void saveBkKss(String ks_ccno, String zjno) {
        List<Bk_ks> bk_ks = bk_ksDao.queryBuilder().where(Bk_ksDao.Properties.Ks_ccno.eq(ks_ccno)).where(Bk_ksDao.Properties.Ks_zjno.eq(zjno)).list();
        LogUtil.i(zjno);
        LogUtil.i(bk_ks);
        LogUtil.i(bk_ks.size());
        for (int i = 0; i < bk_ks.size(); i++) {
            if (bk_ks.get(i) != null) {
                bk_ks.get(i).setIsRZ("1");
                bk_ksDao.update(bk_ks.get(i));
                LogUtil.i("修改成功");
            } else {
                LogUtil.i("数据不存在");
            }
        }
    }

    public void saveRZJG(String jgsj) {
        Sfrz_rzjg rzjgs = rzjgDao.queryBuilder().where(Sfrz_rzjgDao.Properties.Rzjg_time.eq(jgsj)).build().unique();
        if (rzjgs != null) {
            rzjgs.setRzjg_sb("1");
            rzjgDao.update(rzjgs);
            LogUtil.i("修改成功");
        } else {
            LogUtil.i("数据不存在");
        }
    }

    public void saveRZJL(String jlsj) {
        Sfrz_rzjl rzjls = rzjlDao.queryBuilder().where(Sfrz_rzjlDao.Properties.Rzjl_time.eq(jlsj)).build().unique();
        LogUtil.i(jlsj);
        if (rzjls != null) {
            rzjls.setRzjl_sb("1");
            rzjlDao.update(rzjls);
            LogUtil.i("修改成功");
        } else {
            LogUtil.i("数据不存在");
        }
    }


    /**
     * 删除所有数据
     */
    public void deleteAllCjxx() {
        bk_ks_cjxxDao.deleteAll();
    }

    public void deleteAlltemp() {
        bk_ks_tempDao.deleteAll();
    }

    public void deleteAllxp() {
        bk_ksxpDao.deleteAll();
    }

    public void deleteAllzw() {
        kstz_zwDao.deleteAll();
    }

    public void deleteAllcc() {
        ks_ccDao.deleteAll();
    }

    public void deleteAllkc() {
        ks_kcDao.deleteAll();
    }

    public void deleteAllkd() {
        ks_kdDao.deleteAll();
    }

    public void deleteAllkm() {
        ks_kmDao.deleteAll();
    }

    public void deleteAllbkks() {
        bk_ksDao.deleteAll();
    }


    public void deleteAllrzjl() {
        rzjlDao.deleteAll();
    }

    public void deleteAllrzjg() {
        rzjgDao.deleteAll();
    }

    /**
     * 根据id,删除数据
     *
     * @param id 事件id
     */
    public void deleteNote(long id) {
        bk_ks_cjxxDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

}