package com.zhongruan.android.zkfacedemo.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhongruan.android.zkfacedemo.R;
import com.zhongruan.android.zkfacedemo.base.BaseActivity;
import com.zhongruan.android.zkfacedemo.camera.FaceView;
import com.zhongruan.android.zkfacedemo.camera.util.CamParaUtil;
import com.zhongruan.android.zkfacedemo.db.DbServices;
import com.zhongruan.android.zkfacedemo.db.entity.Bk_ks;
import com.zhongruan.android.zkfacedemo.dialog.EditDialog;
import com.zhongruan.android.zkfacedemo.dialog.FaceDialog;
import com.zhongruan.android.zkfacedemo.idcardengine.IDCardData;
import com.zhongruan.android.zkfacedemo.utils.ABLSynCallback;
import com.zhongruan.android.zkfacedemo.utils.DateUtil;
import com.zhongruan.android.zkfacedemo.utils.FileUtils;
import com.zhongruan.android.zkfacedemo.utils.IDCard;
import com.zhongruan.android.zkfacedemo.utils.LogUtil;
import com.zkteco.android.biometric.ZKLiveFaceService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * Created by Administrator on 2017/9/8.
 */

public class RZActivity extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback {
    private LinearLayout mLlBack, mLlChangeCc, layout_view_face, state_camera, include_idcard, tvInputIdCard;
    private TextView mTvCountUnverified, mTvTitle, mTvCountVerified, mTvCountTotal, mTvTime, mTvDate, mTvTip, mTvKsName, mTvKsSeat, mKsResult, mTvKsSfzh, mTvKsKc, mTvKsno;
    private RelativeLayout rl_camera;
    private Button btn_photo;
    private ImageView mIvKs;
    private Handler handler = new Handler();
    private Bitmap bitmap, bit;
    private boolean isRzSucceed;
    private String timeZP, kmno, kmmc, kcmc, kdno, ccmc, ccno;
    private int CS = 0;
    private IDCardData idCardData;
    private Bk_ks bkKs;
    private static int faceID = 0;
    private FaceView faceView;
    private SurfaceView surfaceView;
    private Camera mCamera;
    private SurfaceHolder _surfaceHolder;
    private boolean fileOpiton = false;
    private boolean is = false;
    private boolean contrastOption = false;
    private List<Bk_ks> bk_ks;
    Message message = new Message();
    private int photo = 0;
    private int[] score;
    private String SN = DbServices.getInstance(getBaseContext()).loadAllSN().get(0).getSn();
    private Camera.Size previewSize;
    private int x, y;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_rz);
        startIDCardReader();
        if (idCardData == null) {
            handler.postDelayed(runnable02, 100);
        }
    }

    @Override
    public void initViews() {
        faceView = findViewById(R.id.face_view);
        tvInputIdCard = findViewById(R.id.tv_inputIdCard);
        include_idcard = findViewById(R.id.include_idcard);
        mLlBack = findViewById(R.id.llBack);
        mLlChangeCc = findViewById(R.id.ll_change_cc);
        mTvTitle = findViewById(R.id.tvTitle);
        mTvTime = findViewById(R.id.tvTime);
        mTvDate = findViewById(R.id.tvDate);
        mTvTip = findViewById(R.id.tvTip);
        mTvCountTotal = findViewById(R.id.tvCountTotal);
        mTvCountVerified = findViewById(R.id.tvCountVerified);
        mTvCountUnverified = findViewById(R.id.tvCountUnverified);
        layout_view_face = findViewById(R.id.layout_view_face);
        mIvKs = findViewById(R.id.ivKs);
        mTvKsName = findViewById(R.id.tvKsName);
        mTvKsSeat = findViewById(R.id.tvKsSeat);
        mKsResult = findViewById(R.id.ks_result);
        mTvKsSfzh = findViewById(R.id.tvKsSfzh);
        mTvKsKc = findViewById(R.id.tvKsKc);
        mTvKsno = findViewById(R.id.tvKsno);
        surfaceView = findViewById(R.id.sf_face);
        btn_photo = findViewById(R.id.btn_photo);
        state_camera = findViewById(R.id.state_camera);
        rl_camera = findViewById(R.id.rl_camera);
        MyApplication.getApplication().setShouldStopUploadingData(false);
        include_idcard.setVisibility(View.VISIBLE);
        layout_view_face.setVisibility(View.GONE);
        _surfaceHolder = surfaceView.getHolder();
        _surfaceHolder.addCallback(this);
        _surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        ccno = DbServices.getInstance(getBaseContext()).selectCC().get(0).getCc_no();
        ccmc = DbServices.getInstance(getBaseContext()).selectCC().get(0).getCc_name();
        kcmc = DbServices.getInstance(getBaseContext()).selectKC().get(0).getKc_name();
        kmmc = DbServices.getInstance(getBaseContext()).selectCC().get(0).getKm_name();
        kmno = DbServices.getInstance(getBaseContext()).selectCC().get(0).getKm_no();
        kdno = DbServices.getInstance(getBaseContext()).loadAllkd().get(0).getKd_no();
        mTvTitle.setText(ccmc + " " + kcmc + " " + kmmc);
        KsList();
    }

    @Override
    public void initListeners() {
        mLlBack.setOnClickListener(this);
        mLlChangeCc.setOnClickListener(this);
        btn_photo.setOnClickListener(this);
        tvInputIdCard.setOnClickListener(this);
    }

    @Override
    public void initData() {
        new Thread(runnable01).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llBack:
                if (isRzSucceed) {
                    isZwYz();
                } else {
                    finish();
                }
                break;
            case R.id.ll_change_cc:
                startActivity(new Intent(this, SelectKcCcActivity.class));
                finish();
                break;
            case R.id.btn_photo:
                fileOpiton = true;
                break;
            case R.id.tv_inputIdCard:
                idCardData = null;
                handler.removeCallbacks(runnable02); //停止刷新
                new EditDialog(RZActivity.this, R.style.dialog, new EditDialog.OnEditInputFinishedListener() {
                    @Override
                    public void editInputFinished(Dialog dialog, String password, boolean confirm) {
                        if (confirm) {
                            dialog.dismiss();
                            IDCard idCard = new IDCard();
                            if (idCard.validate_effective(password) == password) {
                                bkKs = DbServices.getInstance(getBaseContext()).selectBKKS(ccno, password);
                                if (bkKs != null) {
                                    bit = FileUtils.getBitmapFromPath(FileUtils.getAppSavePath() + "/" + bkKs.getKs_xp());
                                    register_bitmap(bit);
                                    KsPZ();
                                } else {
                                    ShowToast("未查找到" + password + "，请重试");
                                    handler.postDelayed(runnable02, 100);// 间隔1秒
                                }
                            } else {
                                ShowToast("输入身份证号有误！");
                                handler.postDelayed(runnable02, 100);// 间隔1秒
                            }
                        } else {
                            dialog.dismiss();
                            handler.postDelayed(runnable02, 100);
                        }
                    }
                }).setTitle("请输入身份证号").setInputType(InputType.TYPE_CLASS_NUMBER).show();
                break;
        }
    }

    Runnable runnable01 = new Runnable() {
        @Override
        public void run() {
            mTvTime.setText(DateUtil.getNowTimeNoDate());
            mTvDate.setText(DateUtil.getDateByFormat("yyyy年MM月dd日"));
            handler.postDelayed(runnable01, 1000);
        }
    };

    private Runnable runnable02 = new Runnable() {
        public void run() {
            idCardData = OnBnRead();
            bit = null;
            if (idCardData != null) {
                playBeep();
                bkKs = DbServices.getInstance(getBaseContext()).selectBKKS(ccno, idCardData.getSfzh());
                if (bkKs != null) {
                    register_bitmap(idCardData.getMap());
                    bit = FileUtils.getBitmapFromPath(FileUtils.getAppSavePath() + "/" + bkKs.getKs_xp());
                    KsPZ();
                } else {

                    ShowToast("未查找到" + idCardData.getSfzh() + "，请重试");
                    handler.postDelayed(runnable02, 100);// 间隔1秒
                }
            } else {
                handler.postDelayed(runnable02, 100);// 间隔1秒
            }
        }
    };

    private void register_bitmap(final Bitmap data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int[] detectedFaces = new int[1];
                int ret = ZKLiveFaceService.detectFacesFromBitmap(context, data, detectedFaces);
                if (ret == 0 && detectedFaces[0] > 0) {
                    LogUtil.i("探测人脸成功");
                    getFaceContext();
                } else {
                    LogUtil.i("探测人脸失败");
                    if (photo == 0) {
                        handler.postDelayed(runnable02, 100);// 间隔1秒
                    }
                }
            }
        }).start();
    }

    private void getFaceContext() {
        long[] faceContext = new long[1];
        int ret = 0;
        ret = ZKLiveFaceService.getFaceContext(context, 0, faceContext);
        if (ret == 0) {
            LogUtil.i("人脸", "获取人脸实例成功");
            extractTemplate(faceContext[0]);
        } else {
            LogUtil.i("人脸", "获取人脸实例失败");
            if (photo == 0) {
                handler.postDelayed(runnable02, 100);// 间隔1秒
            }
        }
    }

    private void extractTemplate(long faceContext) {
        int ret = 0;
        byte[] template = new byte[2048];
        int[] size = new int[1];
        int[] resverd = new int[1];
        size[0] = 2048;
        ret = ZKLiveFaceService.extractTemplate(faceContext, template, size, resverd);
        if (ret == 0) {
            LogUtil.i("人脸", "提取模板成功");
            int _ret = 0;
            _ret = ZKLiveFaceService.dbAdd(context, String.valueOf(faceID), template);
            if (0 == _ret) {
                LogUtil.i("人脸", "登记模板成功");
                faceID++;
                photo++;
                if (photo == 1 && bit != null && idCardData != null) {
                    register_bitmap(bit);
                }
            } else {
                LogUtil.i("人脸", "登记模板失败");
                if (photo == 0) {
                    handler.postDelayed(runnable02, 100);// 间隔1秒
                }
            }
        } else {
            LogUtil.i("人脸", "提取模板失败");
            if (photo == 0) {
                handler.postDelayed(runnable02, 100);// 间隔1秒
            }
        }
    }

    private Runnable runnable03 = new Runnable() {
        public void run() {
            state_camera.setVisibility(View.GONE);
            rl_camera.setVisibility(View.VISIBLE);
            contrastOption = true;
        }
    };

    private void KsPZ() {
        include_idcard.setVisibility(View.GONE);
        mTvTip.setText("请看摄像头");
        mLlChangeCc.setEnabled(false);
        layout_view_face.setVisibility(View.VISIBLE);
        Picasso.with(this).load(new File(FileUtils.getAppSavePath() + "/" + bkKs.getKs_xp())).into(mIvKs);
        mTvKsName.setText(bkKs.getKs_xm() + "|" + (bkKs.getKs_xb().equals("1") ? "男" : "女"));
        mTvKsSeat.setText(bkKs.getKs_zwh());
        mTvKsSfzh.setText(bkKs.getKs_zjno());
        mTvKsKc.setText(bkKs.getKs_kcmc());
        mTvKsno.setText(bkKs.getKs_ksno());
        mKsResult.setText("人脸比对中");
        mKsResult.setTextColor(getResources().getColor(R.color.red));
        handler.postDelayed(runnable03, 1000);
        btn_photo.setEnabled(false);
        isRzSucceed = true;
    }

    private void isZwYz() {
        CS = 0;
        photo = 0;
        is = false;
        isRzSucceed = false;
        mTvTip.setText("请刷身份证");
        include_idcard.setVisibility(View.VISIBLE);
        layout_view_face.setVisibility(View.GONE);
        rl_camera.setVisibility(View.GONE);
        state_camera.setVisibility(View.VISIBLE);
    }

    private void KsList() {
        idCardData = null;
        ZKLiveFaceService.dbClear(context);
        mLlChangeCc.setEnabled(true);
        btn_photo.setEnabled(false);
        include_idcard.setVisibility(View.VISIBLE);
        bk_ks = DbServices.getInstance(getBaseContext()).queryBKKSList(kcmc, ccmc);
        mTvCountTotal.setText(bk_ks.size() + "");
        int isRzSize = DbServices.getInstance(getBaseContext()).queryBkKsIsTG(kcmc, ccmc, "1");
        mTvCountVerified.setText(isRzSize + "");
        mTvCountUnverified.setText(bk_ks.size() - isRzSize + "");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        long nTickStart = System.currentTimeMillis();
        OpenCameraAndSetSurfaceviewSize(0);
        LogUtil.i("时间：" + (System.currentTimeMillis() - nTickStart));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mCamera != null) {
            SetAndStartPreview(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private void SetAndStartPreview(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPictureFormat(PixelFormat.JPEG);
            parameters.setPreviewFormat(ImageFormat.NV21);
            mCamera.setPreviewCallback(new _Preview());
            mCamera.startPreview();
            mCamera.cancelAutoFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void OpenCameraAndSetSurfaceviewSize(int cameraId) {
        if (mCamera == null) {
            mCamera = Camera.open(cameraId);
        }
        Camera.Parameters parameters = mCamera.getParameters();
        previewSize = CamParaUtil.getInstance().getPropPreviewSize(parameters.getSupportedPreviewSizes(), 1.333f, 800);
        x = previewSize.width;
        y = previewSize.height;
        parameters.setPreviewSize(previewSize.width, previewSize.height);
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        mCamera.setParameters(parameters);
    }

    class _Preview implements Camera.PreviewCallback {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (contrastOption) {
                contrastOption = false;
                analysis(data);
            }
            if (is) {
                is = false;
                btn_photo.setEnabled(true);
                contrastOption = false;
                ShowToast("人脸比对不通过，请手动拍照");
            }
            Bitmap b = null;
            if (fileOpiton) {
                fileOpiton = false;
                if (null != data) {
                    contrastOption = false;
                    Camera.Size previewSize = camera.getParameters().getPreviewSize();//获取尺寸,格式转换的时候要用到
                    BitmapFactory.Options newOpts = new BitmapFactory.Options();
                    newOpts.inJustDecodeBounds = true;
                    YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21, previewSize.width, previewSize.height, null);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    yuvimage.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 100, baos);// 80--JPG图片的质量[0-100],100最高
                    byte[] rawImage = baos.toByteArray();
                    //将rawImage转换成bitmap
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    b = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options);
                    int x = b.getWidth() / 4;
                    int y = 0;
                    int DST_RECT_WIDTH = b.getWidth() / 2;
                    int DST_RECT_HEIGHT = b.getHeight();
                    final Bitmap bitmap1 = Bitmap.createBitmap(b, x, y, DST_RECT_WIDTH, DST_RECT_HEIGHT);
                    bitmap = ThumbnailUtils.extractThumbnail(Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight()), 168, 240);
                    new FaceDialog(RZActivity.this, R.style.MyDialogStyle, new FaceDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                ShowHintDialog(RZActivity.this, bkKs.getKs_xm() + " 验证通过", "提示", R.drawable.img_base_icon_correct, 800, false);
                                timeZP = DateUtil.getNowTime();
                                ABLSynCallback.call(new ABLSynCallback.BackgroundCall() {
                                    @Override
                                    public Object callback() {
                                        if (DbServices.getInstance(getBaseContext()).selectBKKS(ccno, bkKs.getKs_zjno()).getIsRZ().equals("1") || score[0] > 72) {
                                            return Boolean.valueOf(true);
                                        } else {
                                            return Boolean.valueOf(false);
                                        }
                                    }
                                }, new ABLSynCallback.ForegroundCall() {
                                    @Override
                                    public void callback(Object obj) {
                                        if (((Boolean) obj).booleanValue()) {
                                            DbServices.getInstance(getBaseContext()).saveRzjg("21", bkKs.getKs_ksno(), kmno, kdno, bkKs.getKs_kcno(), bkKs.getKs_zwh(), SN, timeZP, "0");
                                            if (idCardData != null) {
                                                FileUtils.saveBitmap(idCardData.getMap(), "sfrz_rzjl/kstz_a_zw/", bkKs.getKs_zjno() + "_" + DateUtil.getNowTime_Millisecond4());
                                            }
                                            String timezp = DateUtil.getNowTime_Millisecond4();
                                            FileUtils.saveBitmap(bitmap, "sfrz_rzjl/kstz_a_pz/", bkKs.getKs_ksno() + "_" + timezp);
                                            DbServices.getInstance(getBaseContext()).saveRzjl("8007", bkKs.getKs_ksno(), kmno, kdno, bkKs.getKs_kcno(), bkKs.getKs_zwh(), SN, "1", DateUtil.getNowTime(), "", "sfrz_rzjl/kstz_a_pz/" + bkKs.getKs_ksno() + "_" + timezp + ".jpg", DbServices.getInstance(getBaseContext()).selectRzjg(bkKs.getKs_ksno()).toString(), "0");
                                        } else {
                                            DbServices.getInstance(getBaseContext()).saveRzjg("22", bkKs.getKs_ksno(), kmno, kdno, bkKs.getKs_kcno(), bkKs.getKs_zwh(), SN, timeZP, "0");
                                            String timezp = DateUtil.getNowTime_Millisecond4();
                                            FileUtils.saveBitmap(bitmap, "sfrz_rzjl/kstz_a_pz/", bkKs.getKs_ksno() + "_" + timezp);
                                            DbServices.getInstance(getBaseContext()).saveRzjl("8006", bkKs.getKs_ksno(), kmno, kdno, bkKs.getKs_kcno(), bkKs.getKs_zwh(), SN, "0", DateUtil.getNowTime(), "", "sfrz_rzjl/kstz_a_pz/" + bkKs.getKs_ksno() + "_" + timezp + ".jpg", DbServices.getInstance(getBaseContext()).selectRzjg(bkKs.getKs_ksno()).toString(), "0");
                                        }
                                        DbServices.getInstance(getBaseContext()).saveBkKs(kcmc, ccno, bkKs.getKs_zjno());
                                        isZwYz();
                                        KsList();
                                        handler.postDelayed(runnable02, 100);
                                    }
                                });
                                dialog.dismiss();
                            } else {
                                CS = 0;
                                btn_photo.setEnabled(false);
                                contrastOption = true;
                                mKsResult.setText("人脸比对中");
                                mKsResult.setTextColor(getResources().getColor(R.color.red));
                                dialog.dismiss();
                            }
                        }
                    }).setFaceBitmap(bitmap).show();
                }
            }
        }
    }

    private void analysis(final byte[] data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (data != null) {
                    int[] detectedFaces = new int[1];
                    int ret = ZKLiveFaceService.detectFacesFromNV21(context, data, previewSize.width, previewSize.height, detectedFaces);
                    if (ret == 0 && detectedFaces[0] > 0) {
                        LogUtil.i("人脸", "探测人脸成功");
                        _getFaceContext();
                    } else {
                        LogUtil.i("人脸", "探测人脸失败");
                        contrastOption = true;
                    }
                }
            }
        }).start();
    }

    private void _getFaceContext() {
        long[] faceContext = new long[1];
        int ret = 0;
        ret = ZKLiveFaceService.getFaceContext(context, 0, faceContext);
        if (ret == 0) {
            LogUtil.i("人脸", "获取人脸实例成功");
            _extractTemplate(faceContext[0]);
        } else {
            LogUtil.i("人脸", "获取人脸实例失败");
            contrastOption = true;
        }
    }

    private void _extractTemplate(long faceContext) {
        int ret = 0;
        byte[] template = new byte[2048];
        int[] size = new int[1];
        int[] resverd = new int[1];
        size[0] = 2048;
        ret = ZKLiveFaceService.extractTemplate(faceContext, template, size, resverd);
        if (ret == 0) {
            LogUtil.i("人脸", "提取模板成功");
            int[] points = new int[8];
            int _ret = ZKLiveFaceService.getFaceRect(faceContext, points, 4);
            if (_ret == 0) {
                playBeep();
                CS++;
                LogUtil.i("人脸", "成功：" + _ret);
                SendHandle(0, points);
                for (int i = 0; i < points.length; i++) {
                    LogUtil.i(points[i] + "");
                }
            } else {
                LogUtil.i("人脸", "失败：");
                contrastOption = true;
            }
            score = new int[1];
            byte[] faceIDS = new byte[256];
            int[] maxRetCount = new int[1];
            maxRetCount[0] = 1;
            ret = ZKLiveFaceService.dbIdentify(context, template, faceIDS, score, maxRetCount, 72, 100);
            if (ret == 0 && score[0] > 72) {
                LogUtil.i("人脸", "分数:" + score[0] + "比对成功");
                SendHandle(1, null);
            } else {
                LogUtil.i("人脸", "比对失败" + ret);
                SendHandle(2, null);
                if (CS == 3) {
                    is = true;
                    contrastOption = false;
                } else {
                    contrastOption = true;
                }
            }
            SendHandle(3, null);
        } else {
            LogUtil.i("人脸", "提取模板失败");
            contrastOption = true;
        }
    }

    Handler MainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    faceView.setPoints((int[]) msg.obj);
                    faceView.setX(x);
                    faceView.setY(y);
                    break;
                case 1:
                    fileOpiton = true;
                    mKsResult.setText("人脸比对通过");
                    mKsResult.setTextColor(getResources().getColor(R.color.green));
                    break;
                case 2:
                    mKsResult.setText("人脸比对不通过");
                    mKsResult.setTextColor(getResources().getColor(R.color.collect_yellow));
                    break;

                case 3:
                    faceView.clearFaces();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void SendHandle(int what, Object obj) {
        message = MainHandler.obtainMessage();
        message.what = what;
        message.obj = obj;
        MainHandler.sendMessage(message);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isRzSucceed) {
            isZwYz();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable01);
        handler.removeCallbacks(runnable02);
        handler.removeCallbacks(runnable03);
        handler = null;
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        ZKLiveFaceService.dbClear(context);
        fileOpiton = false;
        contrastOption = false;
        OnBnClose();
    }
}
