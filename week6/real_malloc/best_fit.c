
#include <assert.h>
#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void *mmap_from_system(size_t size);
void munmap_to_system(void *ptr, size_t size);

typedef struct metadata_t {
  size_t size;
  struct metadata_t *next;
} metadata_t;

typedef struct heap_t {
  metadata_t *free_head;
  metadata_t dummy;
} heap_t;

heap_t heap;

// 空きリストに追加
void add_to_free_list(metadata_t *metadata) {
  assert(!metadata->next);
  metadata->next = heap.free_head;
  heap.free_head = metadata;
}

// 空きリストから削除
void remove_from_free_list(metadata_t *metadata, metadata_t *prev) {
  if (prev) {
    prev->next = metadata->next;
  } else {
    heap.free_head = metadata->next;
  }
  metadata->next = NULL;
}

void my_initialize() {
  heap.free_head = &heap.dummy;
  heap.dummy.size = 0;
  heap.dummy.next = NULL;
}

void *my_malloc(size_t size) {
  metadata_t *metadata = heap.free_head->next;
  metadata_t *prev = metadata;
  metadata_t *min_metadata = NULL;
  metadata_t *prev_min_metadata = NULL;

  while(metadata){
    prev = metadata;
    metadata = metadata->next;
    if(!metadata) break;

    if(metadata->size >= size){
      if(!min_metadata){
        prev_min_metadata = prev;
        min_metadata = metadata;
      }else if(metadata->size < min_metadata->size){
        prev_min_metadata = prev;
        min_metadata = metadata;
      }
    }
  }
  prev = prev_min_metadata;
  metadata = min_metadata;

  if (!metadata) {
    size_t buffer_size = 4096;
    metadata_t *metadata =
        (metadata_t *)mmap_from_system(buffer_size);
    metadata->size = buffer_size - sizeof(metadata_t);
    metadata->next = NULL;
    add_to_free_list(metadata);
    return my_malloc(size);
  }
  void *ptr = metadata + 1;
  size_t remaining_size = metadata->size - size;
  metadata->size = size;
  remove_from_free_list(metadata, prev);

  if (remaining_size > sizeof(metadata_t)) {
    metadata_t *new_metadata = (metadata_t *)((char *)ptr + size);
    new_metadata->size = remaining_size - sizeof(metadata_t);
    new_metadata->next = NULL;
    add_to_free_list(new_metadata);
  }
  return ptr;
}

// Set the top address to be released.
void my_free(void *ptr) {
  metadata_t *metadata = (metadata_t *)ptr - 1;
  add_to_free_list(metadata);
}

void my_finalize() {}

void test() {
  assert(1 == 1);
}
