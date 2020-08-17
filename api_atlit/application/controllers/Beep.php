<?php
use Restserver\Libraries\REST_Controller;
defined('BASEPATH') OR exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
/** @noinspection PhpIncludeInspection */
//To Solve File REST_Controller not found
require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Beep extends REST_Controller{
    function __construct()
    {
        parent::__construct();
        $this->load->database();
        $this->load->model('M_Beep');
    }
    
    public function deleteAllData_post()
    {
        $bulan      = $this->post('bulan');
        $userid     = $this->post('userid');
        $data       = $this->M_Beep->deleteData($bulan,$userid);
        if($data > 0) {
            $this->response([
                'status'    =>  TRUE,
                'message'   =>  'success'
            ], REST_CONTROLLER::HTTP_OK);
        } else {
            $this->response([
                'status'    =>  FALSE,
                'message'   =>  'failed'
            ], REST_CONTROLLER::HTTP_OK);
        }
    }

    public function getData_post()
    {
        $bulan      = $this->post('bulan');
        $userid     = $this->post('userid');
        $data1      = $this->M_Beep->getData($bulan,"1",$userid);
        $data2      = $this->M_Beep->getData($bulan,"2",$userid);
        $data3      = $this->M_Beep->getData($bulan,"3",$userid);
        $data4      = $this->M_Beep->getData($bulan,"4",$userid);
        $this->response([
            'status'    =>  TRUE,
            'message'   =>  'success',
            'minggu1'   =>  $data1,
            'minggu2'   =>  $data2,
            'minggu3'   =>  $data3,
            'minggu4'   =>  $data4
        ], REST_CONTROLLER::HTTP_OK);
    }

    public function index_post()
    {
        $bulan      = $this->post('bulan');
        $minggu     = $this->post('minggu');
        $nama       = $this->post('nama');
        $umur       = $this->post('umur');
        $jk         = $this->post('jenis_kelamin');
        $level      = $this->post('level');
        $shutle     = $this->post('shuttle');
        $vo2max     = $this->post('vo2max');
        $kebugaran  = $this->post('tingkat_kebugaran');
        $userid     = $this->post('userid');

        $data = [
            "bulan"             => $bulan,
            "minggu"            => $minggu,
            "nama"              => $nama,
            "umur"              => $umur,
            "jenis_kelamin"     => $jk,
            "level"             => $level,
            "shuttle"            => $shutle,
            "vo2max"            => $vo2max,
            "tingkat_kebugaran" => $kebugaran,
            "userid"            => $userid
        ];

        $post = $this->M_Beep->addData($data);
        if($post > 0)
        {
            $this->response([
                'status'    =>  TRUE,
                'message'   =>  'berhasil menambahkan data',
                'data'      =>  $data
            ], REST_CONTROLLER::HTTP_OK);
        }
        else
        {
            $this->response([
                'status'    =>  False,
                'message'   =>  'gagal menambahkan data'
            ], REST_CONTROLLER::HTTP_OK);
        }
    }
}
