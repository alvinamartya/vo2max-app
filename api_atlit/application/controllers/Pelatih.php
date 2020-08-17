<?php
use Restserver\Libraries\REST_Controller;
defined('BASEPATH') OR exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
/** @noinspection PhpIncludeInspection */
//To Solve File REST_Controller not found
require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Pelatih extends REST_Controller{
    function __construct()
    {
        parent::__construct();
        $this->load->database();
        $this->load->model('M_Pelatih');
    }

    public function getDisctint_get()
    {
        $data   = $this->M_Pelatih->getDistinctCabor();
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

    public function AllData_get()
    {
        $data   = $this->M_Pelatih->getAllData();
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

    public function getSingleData_post()
    {
        $refid  = $this->post('refid');
        $data   = $this->M_Pelatih->getData($refid);
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
        $cabang_olahraga    = $this->post('cabang_olahraga');

        $data = [
            'refid'             => $refid,
            'nama'              => $nama,
            'tanggal_lahir'     => $tanggal_lahir,
            'cabang_olahraga'   => $cabang_olahraga
        ];

        $post = $this->M_Pelatih->addData($data);
        $id   = $this->M_Pelatih->lastid();
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

    public function updatedata_post()
    {
        $refid = $this->post('refid');
        $olderData = $this->M_Pelatih->getData($refid);
        if($olderData)
        {
            $nama               = $this->post('nama');
            $tanggal_lahir      = $this->post('tanggal_lahir');
            $cabang_olahraga    = $this->post('cabang_olahraga');

            $data = [
                'nama'              => $nama,
                'tanggal_lahir'     => $tanggal_lahir,
                'cabang_olahraga'   => $cabang_olahraga
            ];

            $put = $this->M_Pelatih->editData($data,$olderData['id']);
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