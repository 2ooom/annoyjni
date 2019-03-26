#include "annoylib.h"
#include "kissrandom.h"

extern "C" {
long createAngular(int f) {
  return (long)new AnnoyIndex<long, float, Angular, Kiss64Random>(f);
}

long createDotProduct(int f) {
  return (long)new AnnoyIndex<long, float, DotProduct, Kiss64Random>(f);
}

long createEuclidean(int f) {
  return (long)new AnnoyIndex<long, float, Euclidean, Kiss64Random>(f);
}

long createManhattan(int f) {
  return (long)new AnnoyIndex<long, float, Manhattan, Kiss64Random>(f);
}

long createHamming(int f) {
  return (long)new HammingWrapper<Kiss64Random>(f);
}

void deleteIndex(long ptr) {
  delete (AnnoyIndexInterface<int32_t, float> *)ptr;
}

void addItem(long ptr, int32_t item, float *w) {
  ((AnnoyIndexInterface<int32_t, float> *)ptr)->add_item(item, w);
}

void build(long ptr, int q) {
  ((AnnoyIndexInterface<int32_t, float> *)ptr)->build(q);
}

bool save(long ptr, char *filename) {
  return ((AnnoyIndexInterface<int32_t, float> *)ptr)->save(filename);
}

void unload(long ptr) {
  ((AnnoyIndexInterface<int32_t, float> *)ptr)->unload();
}

bool load(long ptr, char *filename) {
  return ((AnnoyIndexInterface<int32_t, float> *)ptr)->load(filename);
}

float getDistance(long ptr, int32_t i, int32_t j) {
  return ((AnnoyIndexInterface<int32_t, float> *)ptr)->get_distance(i, j);
}

void getNnsByItem(long ptr, int32_t item, int n,
                  int search_k, int32_t *result, float *distances) {
  vector<int32_t> resultV;
  vector<float> distancesV;
  ((AnnoyIndexInterface<int32_t, float> *)ptr)->get_nns_by_item(item, n, search_k, &resultV, &distancesV);
  std::copy(resultV.begin(), resultV.end(), result);
  std::copy(distancesV.begin(), distancesV.end(), distances);
}

void getNnsByVector(long ptr, float *w, int n,
                    int search_k, int32_t *result, float *distances) {
  vector<int32_t> resultV;
  vector<float> distancesV;
  ((AnnoyIndexInterface<int32_t, float> *)ptr)->get_nns_by_vector(w, n, search_k, &resultV, &distancesV);
  std::copy(resultV.begin(), resultV.end(), result);
  std::copy(distancesV.begin(), distancesV.end(), distances);
}

int getNItems(long ptr) {
  return (int)((AnnoyIndexInterface<int32_t, float> *)ptr)->get_n_items();
}

void verbose(long ptr, bool v) {
  ((AnnoyIndexInterface<int32_t, float> *)ptr)->verbose(v);
}

void getItem(long ptr, int32_t item, float *v) {
  ((AnnoyIndexInterface<int32_t, float> *)ptr)->get_item(item, v);
}
}