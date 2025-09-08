package org.example.project.core

import kotlinx.serialization.Serializable

sealed interface Screen
@Serializable object Home    : Screen
@Serializable object Group   : Screen
@Serializable object Profile : Screen
private val TopTabs = listOf(Home, Group, Profile)

/* Raíces por tab (cada una es el start de su sub-grafo) */
@Serializable object HomeRoot
@Serializable object GroupRoot
@Serializable object ProfileRoot
