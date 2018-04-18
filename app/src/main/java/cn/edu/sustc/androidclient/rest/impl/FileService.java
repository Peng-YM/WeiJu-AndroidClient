package cn.edu.sustc.androidclient.rest.impl;

public class FileService {
//    private static FileService instance;
//    private FileAPI fileAPI;
//
//    public static FileService getInstance(){
//        if (instance == null){
//            synchronized (UserRepository.class){
//                if (instance == null){
//                    instance = new FileService(
//                            RetrofitFactory.getInstance().create(FileAPI.class)
//                    );
//                }
//            }
//        }
//        return instance;
//    }
//
//    private FileService(FileAPI fileAPI){
//        this.fileAPI = fileAPI;
//    }
//
//    public void download(String url, String pathToSave, Observer<File> observer){
//        this.fileAPI
//                .downloadFile(url)
//                .flatMap(processResponse(pathToSave))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//    }
//
//    private Func1<Response<ResponseBody>, Observable<File>> processResponse(final String pathToSave){
//        return new Func1<Response<ResponseBody>, Observable<File>>() {
//            @Override
//            public Observable<File> call(Response<ResponseBody> responseBodyResponse) {
//                return saveToDisk(responseBodyResponse, pathToSave);
//            }
//        };
//    }
//
//    private Observable<File> saveToDisk(final Response<ResponseBody> response, final String pathToSave){
//        return Observable.create(new Observable.OnSubscribe<File>() {
//            @Override
//            public void call(Subscriber<? super File> subscriber) {
//                try{
////                    String headers = response.headers().get("Content-Disposition");
////                    String filename = headers.replace("attachment; filename=", "");
//                    String filename = "Lenna.jpg";
//
//                    new File(pathToSave).mkdir();
//                    File destination = new File(pathToSave + filename);
//                    BufferedSink bufferedSink = Okio.buffer(Okio.sink(destination));
//                    bufferedSink.writeAll(response.body().source());
//                    bufferedSink.close();
//
//                    subscriber.onNext(destination);
//                    subscriber.onCompleted();
//                }catch (IOException | NullPointerException e){
//                    Logger.e(e.getMessage());
//                    subscriber.onError(e);
//                }
//            }
//        });
//    }
//
//    public static void test(){
//        String path = "/data/data/" + MyApplication.getMyPackageName() + "/";
//        String url = "https://pic2.zhimg.com/80/63536f2f01409f750162828a980a0380_hd.jpg";
//        Observer<File> fileObserver = new Observer<File>() {
//            @Override
//            public void onCompleted() {
//                Logger.d("Download completed");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Logger.e(e.getLocalizedMessage());
//            }
//
//            @Override
//            public void onNext(File file) {
//                Logger.d("File downloaded to" + file.getAbsolutePath());
//            }
//        };
//        FileService.getInstance()
//                .download(url, path, fileObserver);
//    }
}
