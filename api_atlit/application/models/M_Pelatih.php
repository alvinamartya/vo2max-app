<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class M_Pelatih extends CI_Model
{
    function getDistinctCabor()
    {
        $query = $this->db->query('SELECT DISTINCT cabang_olahraga from pelatih ORDER BY cabang_olahraga DESC');
        return $query->result_array();
    }

    function getData($refID)
    {
        $this->db->select('id,nama,tanggal_lahir,cabang_olahraga');
        $this->db->where('refid',$refID);
        return $this->db->get('pelatih')->row_array();
    }

    function lastid() {
        $query = $this->db->query('SELECT id from userlogin ORDER BY id DESC LIMIT 1');
        return $query->row_array();
    }

    function getAllData()
    {
        $this->db->select('id,nama');
        return $this->db->get('pelatih')->result_array();
    }

    function addData($data)
    {
        $this->db->insert('pelatih',$data);
        return $this->db->affected_rows();
    }

    function editData($data,$id)
    {
        $this->db->where('id',$id);
        $this->db->update('pelatih',$data);
        return $this->db->affected_rows();
    }

    function deleteData($id)
    {
        $this->db->where('id',$id);
        $this->db->delete('pelatih');
        return $this->db->affected_rows();
    }
}