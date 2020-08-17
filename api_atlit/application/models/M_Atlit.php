<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class M_Atlit extends CI_Model
{
    function getData($refID)
    {
        $query = $this->db->query("select id,nama,tanggal_lahir,tinggi_badan,berat_badan,
        jenis_kelamin from atlit where refid='$refID'");
        return $query ->row_array();
    }

    function getAtlit($cabor) {
        $query = $this->db->query("select a.ID,a.refid,a.nama,a.tanggal_lahir,a.jenis_kelamin,u.id as 
        userid FROM atlit a JOIN userlogin u on u.id = a.refid WHERE a.atlit = 1 and cabang_olahraga = '$cabor'");
        return $query->result_array();
    }

    function lastid() {
        $query = $this->db->query('SELECT id from userlogin ORDER BY id DESC LIMIT 1');
        return $query->row_array();
    }

    function addData($data)
    {
        $this->db->insert('atlit',$data);
        return $this->db->affected_rows();
    }

    function editData($data,$id)
    {
        $this->db->where('id',$id);
        $this->db->update('atlit',$data);
        return $this->db->affected_rows();
    }

    function deleteData($id)
    {
        $this->db->where('id',$id);
        $this->db->delete('atlit');
        return $this->db->affected_rows();
    }
}