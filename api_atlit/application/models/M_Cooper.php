<?php
defined('BASEPATH') or exit('No direct script access allowed');
class M_Cooper extends CI_Model
{
    function addData($data)
    {
        $this->db->insert('cooper', $data);
        return $this->db->affected_rows();
    }

    function getListData($cabor)
    {
        $query = $this->db->query("SELECT * FROM cooper b join atlit a ON b.atlitid = a.ID WHERE b.Solusi is null and a.cabang_olahraga = '$cabor'");
        return $query->result_array();
    }

    function getAtlitID($userid)
    {
        $query = $this->db->query("SELECT id FROM atlit WHERE refid = '$userid'");
        return $query->row_array();
    }

    function editData($data, $id)
    {
        $this->db->where('id', $id);
        $this->db->update('cooper', $data);
        return $this->db->affected_rows();
    }

    function getData($bulan, $minggu, $userid)
    {
        $this->db->where('bulan', $bulan);
        $this->db->where('minggu', $minggu);
        $this->db->where('userid', $userid);
        $this->db->order_by('umur', 'asc');
        return $this->db->get('cooper')->result_array();
    }

    function getDataPelatih($bulan, $minggu)
    {
        $this->db->where('bulan', $bulan);
        $this->db->where('minggu', $minggu);
        $this->db->order_by('umur', 'asc');
        return $this->db->get('cooper')->result_array();
    }

    function deleteData($bulan, $atlitid)
    {
        $this->db->where('bulan', $bulan);
        $this->db->where('atlitid', $atlitid);
        $this->db->delete('cooper');
        return $this->db->affected_rows();
    }
}
