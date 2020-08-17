<?php
use Restserver\Libraries\REST_Controller;
defined('BASEPATH') OR exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
/** @noinspection PhpIncludeInspection */
//To Solve File REST_Controller not found
require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Atlit extends REST_Controller{
    function __construct()
    {
        parent::__construct();
        $this->load->database();
        $this->load->model('M_Atlit');
    }

    public function getSingleData_post()
    {
        $refid  = $this->post('refid');
        $data   = $this->M_Atlit->getData($refid);
        if($data)
        {
            $this->response([
                'status'    =>  TRUE,
                'message'   =>  'data berhasil ditemukan',
                'data'      =>  $data
            ], REST_CONTROLLER::HTTP_OK);
        } 
        else 
        {
            $this->response([
                'status'    =>  False,
                'message'   =>  'data tidak ditemukan'
            ], REST_CONTROLLER::HTTP_OK);
        }
    }

    public function index_post()
    {
        $refid              = $this->post('refid');
        $nama               = $this->post('nama');
        $tanggal_lahir      = $this->post('tanggal_lahir');
        $tinggi_badan       = $this->post('tinggi_badan');
        $berat_badan        = $this->post('berat_badan');
        $jenis_kelamin      = $this->post('jenis_kelamin');
        $atlit              = $this->post('atlit');
        $cabang_olahraga    = $this->post('cabang_olahraga');

        $data = [
            'refid'             => $refid,
            'nama'              => $nama,
            'tanggal_lahir'     => $tanggal_lahir,
            'tinggi_badan'      => $tinggi_badan,
            'berat_badan'       => $berat_badan,
            'jenis_kelamin'     => $jenis_kelamin,
            'atlit'             => $atlit,
            'cabang_olahraga'   => $cabang_olahraga
        ];

        $post = $this->M_Atlit->addData($data);
        $id   = $this->M_Atlit->lastid();
        if($post > 0)
        {
            $this->response([
                'status'    =>  TRUE,
                'message'   =>  'berhasil menambahkan data',
                'data'      =>  $data,
                'id'        =>  $id['id']
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

    public function getData_post()
    {
        $cabor = $this->post('cabor');
        $data = $this->M_Atlit->getAtlit($cabor);
        if($data)
        {
            $this->response([
                'status'    =>  TRUE,
                'message'   =>  'success',
                'data'      =>  $data
            ], REST_CONTROLLER::HTTP_OK);
        } 
        else 
        {
            $this->response([
                'status'    =>  False,
                'message'   =>  'failed'
            ], REST_CONTROLLER::HTTP_OK);
        }
    }

    public function updatedata_post()
    {   
        $refid = $this->post('refid');
        $olderData = $this->M_Atlit->getData($refid);
        if($olderData)
        {
            $nama           = $this->post('nama');
            $tanggal_lahir  = $this->post('tanggal_lahir');
            $tinggi_badan   = $this->post('tinggi_badan');
            $berat_badan    = $this->post('berat_badan');
            $jenis_kelamin  = $this->post('jenis_kelamin');

            $data = [
                'nama'          => $nama,
                'tanggal_lahir' => $tanggal_lahir,
                'tinggi_badan'  => $tinggi_badan,
                'berat_badan'   => $berat_badan,
                'jenis_kelamin' => $jenis_kelamin
            ];

            $put = $this->M_Atlit->editData($data,$olderData['id']);
            if($put > 0)
            {
                $this->response([
                    'status'    =>  TRUE,
                    'message'   =>  'berhasil mengubah data',
                    'data'      =>  $data
                ], REST_CONTROLLER::HTTP_OK);
            }
            else
            {
                $this->response([
                    'status'    =>  False,
                    'message'   =>  'gagal mengubah data'
                ], REST_CONTROLLER::HTTP_OK);
            }
        }
        else
        {
            $this->response([
                'status'    =>  False,
                'message'   =>  'data tidak ditemukan'
            ], REST_CONTROLLER::HTTP_OK);
        }
    }
}