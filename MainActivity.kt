package com.example.homew1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homew1.databinding.ActivityMainBinding
import com.example.homew1.databinding.BottomSheetBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomSheetBinding: BottomSheetBinding
    private lateinit var adapter: ItemAdapter
    private val items = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ItemAdapter(
            clickListener = { item ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("item", item)
                startActivity(intent)
            },
            longClickListener = { item ->
                showDeleteDialog(item)
            }
        )

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        binding.recyclerView.addItemDecoration(SpacingItemDecoration(16))

        binding.buttonList.setOnClickListener {
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }

        binding.buttonGrid.setOnClickListener {
            binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        }

        binding.buttonGrid2.setOnClickListener {
            binding.recyclerView.layoutManager = CustomGridLayoutManager(this, null, 0, 0)
        }

        binding.fab.setOnClickListener {
            showBottomSheet()
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                items.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        generateInitialItems()
    }

    private fun generateInitialItems() {
        val textList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        val imageList = R.drawable.ic_add2

        for (i in 1..25) {
            val text = textList.random()
            items.add(Item(text, imageList))
        }

        adapter.submitList(items)
    }

    private fun showBottomSheet() {
        val bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    fun addRandomItems(quantity: Int) {
        val newItems = generateItems(quantity)
        val positions = mutableListOf<Int>()
        for (i in 1..quantity) {
            val position = Random.nextInt(items.size + 1)
            positions.add(position)
            items.add(position, newItems[i - 1])
        }
        adapter.notifyItemRangeInserted(positions.minOrNull() ?: 0, quantity)
    }

    fun removeRandomItems(quantity: Int) {
        val countToRemove = minOf(quantity, items.size)
        val positions = mutableListOf<Int>()
        for (i in 1..countToRemove) {
            val position = Random.nextInt(items.size)
            positions.add(position)
            items.removeAt(position)
        }
        adapter.notifyItemRangeRemoved(positions.minOrNull() ?: 0, countToRemove)
    }

    fun addOneItem() {
        val newItem = generateItems(1).first()
        val position = Random.nextInt(items.size + 1)
        items.add(position, newItem)
        adapter.notifyItemInserted(position)
    }

    fun removeOneItem() {
        if (items.isNotEmpty()) {
            val position = Random.nextInt(items.size)
            items.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
    }

    private fun generateItems(count: Int): List<Item> {
        val textList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        val imageList = R.drawable.ic_add2
        val newItems = mutableListOf<Item>()
        for (i in 1..count) {
            val text = textList.random()
            newItems.add(Item(text, imageList))
        }
        return newItems
    }

    private fun showDeleteDialog(item: Item) {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Delete Item")
        builder.setMessage("Are you sure you want to delete this item?")
        builder.setPositiveButton("Yes") { dialog, which ->
            val position = items.indexOf(item)
            if (position != -1) {
                items.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }
}
